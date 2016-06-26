function initGameHall() {


    var gameListVue = new Vue({
        el: '#gameListVue',
        data: {
            items: []
        },
        methods: {
            updateGameList: function () {
                var self = this;
                $.getJSON("/getAllGames", function (data) {
                    // console.log(data)
                    if (data.success) {
                        self.items = data.result;
                        setTimeout(self.updateGameList, 5000);
                    }
                });
            }
        },
        ready: function () {
            this.updateGameList();
        }
    })

    var playerListVue = new Vue({
        el: '#playerListVue',
        data: {
            items: []
        },
        methods: {
            updatePlayerList: function () {
                var self = this;
                $.getJSON("/getAllUsers", function (data) {
                    // console.log(data)
                    if (data.success) {
                        self.items = data.result;
                        setTimeout(self.updatePlayerList, 5000);
                    }
                });
            }
        },
        ready: function () {
            this.updatePlayerList();
        }
    })

    var gameHallControl = new Vue({
        el: '#gameHallControl',
        data: {},
        methods: {
            startCreateGame: function () {
                var self = this;
                var simpleProtocol = {};
                simpleProtocol.action = 'create_game';
                sendMessage(JSON.stringify(simpleProtocol));
                this.finishCreateGame();
            },

            finishCreateGame: function () {
                $("#gameListArea").hide();
                $("#gamePlayArea").show();
            }
        }
    })


}
