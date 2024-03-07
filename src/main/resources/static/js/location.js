var map;


async function initMap() {
    map = new google.maps.Map(document.getElementById('location_map'), {
        zoom: 12,
        center: { lat: 16.8409, lng: 96.1735 },
    });


    var input = document.getElementById('search-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    map.addListener('bounds_changed', function() {
        searchBox.setBounds(map.getBounds());
    });

    searchBox.addListener('places_changed', function() {
        var places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

       addMarker();
    });

    map.addListener('click', function(event) {
        var clickedLocation = event.latLng;
        var latitude = clickedLocation.lat();
        var longitude = clickedLocation.lng();
        window.location.href = '/customers/create-address?latitude=' + latitude + '&longitude=' + longitude;
    });
}

function addMarker(location, title) {
    var marker = new google.maps.Marker({
        position: location,
        map: map,
        title: title
    });
}



$(window).on('load', function() {
    initMap();
});