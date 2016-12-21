(function (exports) {
    exports.app = new Vue({
        el: '#content',
        data: {
            measurements: null,
            dataReady: false
        },
        ready: function () {
            this.fetchData();
        },
        methods: {
            fetchData: function () {
                var vm = this;
                this.$http.get('measurements' + location.search).then(function (response) {
                    vm.measurements = response.body;
                    vm.dataReady = true;

                    var ctx = document.getElementById("chart");
                    var labels = [];
                    var data = [];
                    var i = 0;
                    this.measurements.forEach(function (m) {
                        labels[i] = m.formatTimestamp;
                        data[i] = m.duration;
                        i++;
                    });

                    var data = {
                        labels: labels,
                        datasets: [
                            {
                                label: 'Duration',
                                fill: false,
                                lineTension: 0.1,
                                backgroundColor: "rgba(75,192,192,0.4)",
                                borderColor: "rgba(75,192,192,1)",
                                borderCapStyle: 'butt',
                                borderDash: [],
                                borderDashOffset: 0.0,
                                borderJoinStyle: 'miter',
                                pointBorderColor: "rgba(75,192,192,1)",
                                pointBackgroundColor: "#fff",
                                pointBorderWidth: 1,
                                pointHoverRadius: 5,
                                pointHoverBackgroundColor: "rgba(75,192,192,1)",
                                pointHoverBorderColor: "rgba(220,220,220,1)",
                                pointHoverBorderWidth: 2,
                                pointRadius: 1,
                                pointHitRadius: 10,
                                spanGaps: false,
                                data: data
                            }
                        ]
                    };
                    Chart.Line(ctx, {
                        data: data
                    });
                }, function (response) {
                    console.log(response.status);
                });
            }
        }
    });
})(window);
