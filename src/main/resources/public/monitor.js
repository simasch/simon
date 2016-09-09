window.onload = function () {
    check();
}

function check() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'check', true);
    xhr.onload = function () {
        var hosts = JSON.parse(xhr.responseText);

        var html = '<h1>' + hosts.name + '</h1>';
        hosts.group.forEach(function (group) {
            html += '<h2>' + group.name + '</h2>';
            group.host.forEach(function (host) {
                var color = host.status > 200 ? 'error' : 'success';
                html += '<table><tr class="' + color + '">';
                html += '<td width="200">' + host.name + '</td>';
                html += '<td width="300">' + host.url + '</td>';
                html += '<td width="100">' + host.status + '</td>';
                html += '<td width="100" align="right">' + host.time + ' ms</td>';
                html += '</tr></table>';
            });
        });
        html += "<br>";

        html += '<p>Last refresh: ' + formatTimestamp() + '</p>';

        document.getElementById('content').innerHTML = html;
    };
    xhr.send();
}

var interval;
var autorefresh = false;

function switchAutoRefresh() {
    autorefresh = !autorefresh;

    var text = autorefresh ? 'Toggle auto refresh off' : 'Toggle auto refresh on';
    document.getElementById('autorefresh').value = text;

    if (autorefresh) {
        interval = setInterval(function () {
            check();
        }, 10000);
    } else {
        clearInterval(interval);
    }
}

function formatTimestamp() {
    var date = new Date();
    return pad(date.getDate()) + '.' + pad((date.getMonth() + 1)) + '.' + date.getFullYear() + ' '
            + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
}

function pad(value) {
    if (value < 10) {
        return '0' + value;
    } else {
        return value;
    }
}