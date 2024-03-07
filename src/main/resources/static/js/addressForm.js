 $(document).ready(function() {
    // Function to get query parameters from URL
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    // Get latitude and longitude values from URL
    var latitude = getUrlParameter('latitude');
    var longitude = getUrlParameter('longitude');

    // Populate latitude and longitude fields in the form
    $('#latitude').val(latitude);
    $('#longitude').val(longitude);
});
 if (typeof(Storage) !== "undefined") {
     // Function to save form data to local storage
     function saveFormDataToLocalStorage(formData) {
         localStorage.setItem("addressFormData", JSON.stringify(formData));
     }

     // Function to retrieve form data from local storage
     function getFormDataFromLocalStorage() {
         var formData = localStorage.getItem("addressFormData");
         return JSON.parse(formData);
     }
 }
