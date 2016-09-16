window.onload = function () {
    check();
};

function check() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'check/latest', true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {

        new Vue({
            el: '#content',
            data: {
                host: JSON.parse(xhr.responseText)
            }
        });
        
        document.getElementById('lastrefresh').innerHTML = 'Last refresh: ' + formatTimestamp(new Date());
    };
    xhr.send();
}

var interval;
var autorefresh = false;

function switchAutoRefresh() {
    autorefresh = !autorefresh;

    var text = 'Turn auto refresh';
    text += autorefresh ? ' off' : ' on';
    document.getElementById('autorefresh').innerHTML = text;

    if (autorefresh) {
        interval = setInterval(function () {
            check();
        }, 10000);
    } else {
        clearInterval(interval);
    }
}

function formatTimestamp(date) {
    return pad(date.getDate()) + '.' + pad((date.getMonth() + 1)) + '.' + date.getFullYear() + ' '
            + pad(date.getHours()) + ':' + pad(date.getMinutes()) + ':' + pad(date.getSeconds());
}

function pad(value) {
    if (value < 10) {
        return '0' + value;
    } else {
        return value;
    }
}

