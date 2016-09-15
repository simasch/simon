window.onload = function () {
    check();
};

function check() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'latest', true);
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

            var table = document.createElement('table');
            var header = table.createTHead();
            var hRow = header.insertRow(0);
            hRow.className = 'header';
            var hCellName = hRow.insertCell(0);
            hCellName.innerHTML = 'Name';
            var hCellUrl = hRow.insertCell(1);
            hCellUrl.innerHTML = 'URL';
            var hCellStatus = hRow.insertCell(2);
            hCellStatus.align = 'right';
            hCellStatus.innerHTML = 'Status';
            var hCellDuration = hRow.insertCell(3);
            hCellDuration.align = 'right';
            hCellDuration.innerHTML = 'Duration';
            var hCellTimestamp = hRow.insertCell(4);
            hCellTimestamp.align = 'right';
            hCellTimestamp.innerHTML = 'Last check';

            var i = 1;
            group.host.forEach(function (host) {
                var row = table.insertRow(i);
                row.className = isNaN(host.status) || host.status > 200 ? 'error' : 'success';

                var cellName = row.insertCell(0);
                cellName.width = 200;
                cellName.innerHTML = host.name;

                var cellUrl = row.insertCell(1);
                cellUrl.width = 300;
                var aUrl = document.createElement('a');
                aUrl.href = host.url;
                aUrl.title = host.url;
                aUrl.target = '_new';
                var aUrlText = document.createTextNode(host.url);
                aUrl.appendChild(aUrlText);
                cellUrl.appendChild(aUrl);

                var cellStatus = row.insertCell(2);
                cellStatus.width = 100;
                cellStatus.align = 'right';
                cellStatus.innerHTML = host.status;

                var cellDuration = row.insertCell(3);
                cellDuration.width = 100;
                cellDuration.align = 'right';
                var aDuration = document.createElement('a');
                aDuration.href = '#';
                aDuration.onclick = function () {
                    showMeasurements(host.url);
                }
                var aDurationText = document.createTextNode(host.duration + ' ms');
                aDuration.appendChild(aDurationText);
                cellDuration.appendChild(aDuration);

                var cellTimestamp = row.insertCell(4);
                cellTimestamp.width = 200;
                cellTimestamp.align = 'right';
                cellTimestamp.innerHTML = host.timestamp;

                i++;
            });
            content.appendChild(table);
        });
        content.appendChild(document.createElement('br'));

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

function showMeasurements(url) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", 'measurements?url=' + url, true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.onload = function () {
        var measurements = JSON.parse(xhr.responseText);

        var info = url + '\n';

        measurements.forEach(function (m) {
            var dateTime = formatTimestamp(new Date(m.timestamp));
            info += dateTime + ': ' + m.status + ' ' + m.duration + ' ms\n';
        });

        alert(info);
    };
    xhr.send();
}