window.onload = function () {
    check();
}

function check() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'check', true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {
        var hosts = JSON.parse(xhr.responseText);

        var content = document.getElementById('content');
        content.innerHTML = '';

        var h1 = document.createElement('h1');
        h1.innerHTML = hosts.name;
        content.appendChild(h1);

        hosts.group.forEach(function (group) {
            var h2 = document.createElement('h2');
            h2.innerHTML = group.name;
            content.appendChild(h2);

            var i = 0;
            var table = document.createElement('table');
            group.host.forEach(function (host) {
                var row = table.insertRow(i);
                row.className = isNaN(host.status) || host.status > 200 ? 'error' : 'success';
                var cellName = row.insertCell(0);
                cellName.width = 200;
                cellName.innerHTML = host.name;
                var cellUrl = row.insertCell(1);
                cellUrl.width = 300;
                var hostLink = document.createElement('a');
                hostLink.href = host.url;
                hostLink.title = host.url;
                hostLink.target = '_new';
                var hostLinkText = document.createTextNode(host.url);
                hostLink.appendChild(hostLinkText);
                cellUrl.appendChild(hostLink);
                var cellStatus = row.insertCell(2);
                cellStatus.width = 100;
                cellStatus.align = 'right';
                cellStatus.innerHTML = host.status;
                var cellTime = row.insertCell(3);
                cellTime.width = 100;
                cellTime.align = 'right';
                cellTime.innerHTML = host.time + ' ms';
                i++;
            });
            content.appendChild(table);
        });
        content.appendChild(document.createElement('br'));

        var pRefresh = document.createElement('p');
        pRefresh.innerHTML = formatTimestamp();
        content.appendChild(pRefresh);
    };
    xhr.send();
}

var interval;
var autorefresh = false;

function switchAutoRefresh() {
    autorefresh = !autorefresh;

    var text = 'Toggle auto refresh';
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

function formatTimestamp() {
    var date = new Date();
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