var map;
var markers = [];

async function initMap() {
    map = new google.maps.Map(document.getElementById('customer_map'), {
        zoom: 12,
        center: { lat: 0, lng: 0 },
    });

    fetchAllCustomers();
}

$(document).ready(function() {
    // Autocomplete functionality
    $('#customerSearch').on('input', function() {
        var searchTerm = $(this).val().toLowerCase();
        if (searchTerm.length > 0) {
            $.get('/customers/autocomplete?searchTerm=' + searchTerm, function(data) {
                $('#customerTable tbody').empty();
                data.forEach(function(customer) {
                    var addressInfo = '';
                    if (customer.addresses && customer.addresses.length > 0) {
                        addressInfo = customer.addresses.map(function(address) {
                            return address.street + ', ' + address.city;
                        }).join('<br>');
                    }
                    var customerRow = '<tr>' +
                        '<td>' + customer.name + '</td>' +
                        '<td>' + customer.email + '</td>' +
                        '<td>' + customer.phone + '</td>' +
                        '<td>' + addressInfo + '</td>' +
                        '<td><a href="#" class="btn btn-primary btn-sm" onclick="viewCustomerDetail(' + customer.id + ')">Detail</a></td>' +
                        '</tr>';
                    $('#customerTable tbody').append(customerRow);
                });
            });
        } else {
            fetchAllCustomers();
        }
    });
});

function fetchAllCustomers() {
    removeBoundaryCircles();
    $.get("/customers/customer-list", function(data) {
        removeAllMarkers();
        displayCustomers(data);
        updateCustomerCount(data.length);
        if (Array.isArray(data) && data.length > 0) {
            var bounds = new google.maps.LatLngBounds();

            data.forEach(function(customer) {
                if (customer.addresses && Array.isArray(customer.addresses)) {
                    customer.addresses.forEach(function(address) {
                        if (address.latitude && address.longitude) {
                            var coordinates = { lat: address.latitude, lng: address.longitude };
                            placeMarker(coordinates, customer.name);
                            bounds.extend(coordinates);
                        } else {
                            console.error("Invalid latitude or longitude for address:", address.id);
                        }
                    });
                } else {
                    console.error("No addresses found for customer:", customer.name);
                }
            });

            map.fitBounds(bounds);
            map.panToBounds(bounds);
        } else {
            console.error("No customers found in the response.");
        }
    }).fail(function() {
        console.error("Failed to fetch all customers data.");
    });
}

function searchCustomers() {
    var searchTerm = $("#customerSearch").val().toLowerCase();
    $.get("/customers/found-customer?name=" + searchTerm, function(data) {
        displayCustomers(data);
        if (Array.isArray(data) && data.length > 0) {
            var bounds = new google.maps.LatLngBounds();
            removeAllMarkers();

            data.forEach(function(customer) {
                if (customer.addresses && Array.isArray(customer.addresses)) {
                    customer.addresses.forEach(function(address) {
                        if (address.latitude && address.longitude) {
                            var coordinates = { lat: address.latitude, lng: address.longitude };
                            placeMarker(coordinates, customer.name);
                            bounds.extend(coordinates);
                        } else {
                            console.error("Invalid latitude or longitude for address:", address.id);
                        }
                    });
                } else {
                    console.error("No addresses found for customer:", customer.name);
                }
            });


            var predefinedZoomLevel = 18;
            map.setZoom(predefinedZoomLevel);

            var firstCustomer = data[0];
            if (firstCustomer && firstCustomer.addresses && firstCustomer.addresses.length > 0) {
                var firstAddress = firstCustomer.addresses[0];
                var centerCoordinates = { lat: firstAddress.latitude, lng: firstAddress.longitude };
                map.setCenter(centerCoordinates);
            }
        } else {
            console.error("No customers found matching the search term.");
        }
    }).fail(function() {
        console.error("Failed to fetch filtered customers.");
    });
}

function showBoundaryAndCustomers() {
    var latitude = parseFloat(document.getElementById('latitude').value);
    var longitude = parseFloat(document.getElementById('longitude').value);

    if (isNaN(latitude) || isNaN(longitude)) {
        console.error("Latitude or longitude is not a valid number.");
        return;
    }

    removeBoundaryCircles();

    var dynamicRadius = calculateDynamicRadius(latitude, longitude);

    var boundary = new google.maps.Circle({
        strokeColor: '#0b5ed7',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#0b5ed7',
        fillOpacity: 0.35,
        map: map,
        center: { lat: latitude, lng: longitude },
        radius: dynamicRadius
    });


    $.get("/customers/in-range?latitude=" + latitude + "&longitude=" + longitude, function(data) {
        displayCustomers(data);
        removeAllMarkers();

        if (data.length > 0) {
            var bounds = new google.maps.LatLngBounds();

            data.forEach(function(customer) {
                customer.addresses.forEach(function(address) {
                    var addressLat = parseFloat(address.latitude);
                    var addressLng = parseFloat(address.longitude);

                    if (!isNaN(addressLat) && !isNaN(addressLng)) {
                        var marker = new google.maps.Marker({
                            position: {lat: addressLat, lng: addressLng},
                            map: map,
                            title: customer.name + " - " + address.street
                        });

                        bounds.extend(new google.maps.LatLng(addressLat, addressLng));
                    } else {
                        console.error("Invalid latitude or longitude for address:", address.id);
                    }
                });
            });


            map.fitBounds(bounds);
            map.panToBounds(bounds);

            updateCustomerCount(data.length);
        } else {
            var infowindow = new google.maps.InfoWindow({
                content: "No customers found in this range"
            });
            infowindow.setPosition({lat:latitude,lng:longitude});
            infowindow.open(map);
            console.error("No customers found in the specified area.");
            updateCustomerCount(data.length);
        }
    }).fail(function() {
        console.error("Failed to fetch customers within the specified range.");
    });
}

function updateCustomerCount(count) {
    $('#customerCount').html('<i class="fas fa-user me-2"></i>' + count);
}

function calculateDynamicRadius(latitude, longitude) {
    var areaFactor = 1.5;
    var townshipArea = 5000000 * areaFactor;
    var townshipAreaKm2 = townshipArea / 1000000;
    var dynamicRadius = Math.sqrt(townshipAreaKm2 / Math.PI) * 1000;

    return dynamicRadius;
}

function displayCustomers(customers) {
    $('#customerTable tbody').empty();
    customers.forEach(function(customer) {
        var addressInfo = '';
        if (customer.addresses && customer.addresses.length > 0) {
            addressInfo = customer.addresses.map(function(address) {
                return address.street + ', ' + address.city;
            }).join('<br>');
        }

        var customerRow = '<tr>' +
            '<td>' + customer.name + '</td>' +
            '<td>' + customer.email + '</td>' +
            '<td>' + customer.phone + '</td>' +
            '<td>' + addressInfo + '</td>' +
            '<td><a href="#" class="btn btn-primary btn-sm" onclick="viewCustomerDetail(' + customer.id + ')">Detail</a></td>' +
            '</tr>';
        $('#customerTable tbody').append(customerRow);
    });
}

function viewCustomerDetail(customerId) {
    window.location.href = '/customer-detail?id=' + customerId;
}

function placeMarker(location, name) {
    var marker = new google.maps.Marker({
        position: location,
        map: map,
        title: name
    });
    markers.push(marker);
}


function removeBoundaryCircles() {
    map.overlayMapTypes.forEach(function(overlay) {
        if (overlay instanceof google.maps.Circle) {
            overlay.setMap(null);
        }
    });
}


function removeAllMarkers() {
    markers.forEach(function(marker) {
        marker.setMap(null);
    });
    markers = [];
}

$(window).on('load', function() {
    initMap();
});