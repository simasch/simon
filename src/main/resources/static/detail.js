window.onload = function () {
    load();
};

function load() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'measurements' + location.search, true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {

        new Vue({
            el: '#content',
            data: {
                measurements: JSON.parse(xhr.responseText)
            }
        });
    };
    xhr.send();
}
