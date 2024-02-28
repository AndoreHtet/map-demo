var map;
var markers = [];

async function initMap() {
    map = new google.maps.Map(document.getElementById('map_div'), {
        center: { lat: 16.8409, lng: 96.1735 },
        zoom: 8
    });


    fetchAllStatesData();
}

function fetchAllStatesData() {
    $.get("/states/all", function(data) {
        removeAllMarkers();


        if (Array.isArray(data) && data.length > 0) {
            var bounds = new google.maps.LatLngBounds();

            data.forEach(function(state) {
                if (state.latitude && state.longitude) {
                    var coordinates = { lat: state.latitude, lng: state.longitude };
                    placeMarker(coordinates);
                    bounds.extend(coordinates);
                } else {
                    console.error("Invalid latitude or longitude for state:", state.name);
                }
            });



            map.fitBounds(bounds);
            map.panToBounds(bounds);


            displayAllStatesList(data);
        } else {
            console.error("No states found in the response.");
        }
    }).fail(function() {
        console.error("Failed to fetch all states data.");
    });
}

function displayAllStatesList(states) {
    var listItems = states.map(function(state) {
        return '<li class="list-group-item">' + state.name + '</li>';
    });
    $('#allStatesList').html(listItems.join(''));
}

$(document).ready(function() {
    $('#searchButton').click(function() {
        var selectedState = $('#selectState').val();

        if (!selectedState) {
            fetchAllStatesData();
            return;
        }


        $.get("/states/filter?name=" + selectedState, function(data) {
            removeAllMarkers();

            if (data.length > 0) {
                var state = data[0];
                var coordinates = { lat: state.latitude, lng: state.longitude };
                placeMarker(coordinates);
                map.setCenter(coordinates);
                $('#allStatesList').html('<li class="list-group-item">' + state.name  +'</li>');
            } else {
                $('#allStatesList').html('<li class="list-group-item">No state found</li>');
            }
        }).fail(function() {
            console.error("Failed to fetch filtered states.");
        });
    });

    $('#allStatesButton').click(function() {
        fetchAllStatesData();
        map.setCenter({ lat: 16.8409, lng: 96.1735 });
    });
});

function placeMarker(location) {
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
    markers.push(marker);
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