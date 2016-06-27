//游戏大厅对战列表
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
        },
        startJoinGame: function ($index) {
            var simpleProtocol = {};
            simpleProtocol.action = "join_game";
            simpleProtocol.content = $index;
            sendMessage(JSON.stringify(simpleProtocol));
        },
        finishJoinGame: function () {
            $("#gameListArea").hide();
            $("#gamePlayArea").show();
            gamePlayRightVue.updategamePlayRight("", userName);
        }
    },
    ready: function () {
        this.updateGameList();
    }
})

//游戏大厅玩家列表
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


//游戏大厅控制器(创建游戏)
var gameHallControl = new Vue({
    el: '#gameHallControl',
    data: {},
    methods: {
        //创建游戏
        startCreateGame: function () {
            var self = this;
            var simpleProtocol = {};
            simpleProtocol.action = 'create_game';
            sendMessage(JSON.stringify(simpleProtocol));
            // this.finishCreateGame();
        },

        finishCreateGame: function () {
            $("#gameListArea").hide();
            $("#gamePlayArea").show();
            gamePlayRightVue.updategamePlayRight(userName, "");
        }
    }
})

//进入游戏界面后,右侧对战情况
var gamePlayRightVue = new Vue({
    el: '#gamePlayRightVue',
    data: {
        creator: {},
        joiner: {}
    },
    methods: {
        updategamePlayRight: function ($index) {
            var self = this;
            $.getJSON("/getGameInfo", $index, function (data) {
                if (data.success) {
                    self.creator = data.result.creator;
                    self.joiner = data.result.joiner;
                }
            });
        }
    }
})

var chessboardVue = new Vue({
    el: '#chessboardVue',
    data: {
        gridSize: 50,
        validateSize: 700,
        //vue不支持动态初始化?坑..
        // gapSize: this.gridSize / 2,
        // boardSize: this.validateSize + this.gapSize * 2,
        gapSize: 25,
        boardSize: 800,
        pieceColor: "#000"
    },
    methods: {
        initChessBoard: function () {
            var stage = new createjs.Stage("chessboardVue");
            stage.enableMouseOver();
            // var backgroud = new createjs.Shape();
            // backgroud.x = 0;
            // backgroud.y = 0;
            // backgroud.graphics
            //   .beginFill("BlanchedAlmond").drawRect(0, 0, boardSize, boardSize);
            // stage.addChild(backgroud);
            var gridCount = this.validateSize / this.gridSize;
            var self = this;
            for (var row = 0; row <= gridCount; ++row) {
                for (var col = 0; col <= gridCount; ++col) {
                    var piece = new createjs.Shape();
                    this.resetPiece(piece);
                    piece.x = col * this.gridSize + this.gapSize - this.gridSize / 2;
                    piece.y = row * this.gridSize + this.gapSize - this.gridSize / 2;
                    piece.on("click", function (event) {
                        if (!event.target.isChecked) {
                            self.checkPiece(event.target);
                            stage.update();
                        }
                    });
                    piece.on("mouseover", function (event) {
                        if (!event.target.isChecked) {
                            self.precheckPiece(event.target);
                            stage.update();
                        }
                    });
                    piece.on("mouseout", function (event) {
                        if (!event.target.isChecked) {
                            self.resetPiece(event.target);
                            stage.update();
                        }
                    });
                    stage.addChild(piece);
                }
            }
            stage.x = 0;
            stage.y = 0;
            stage.update();
            function setPiece(row, col, color) {
                // body...
            }
        }
        ,
        resetPiece: function (piece) {
            piece.graphics
                .beginFill("BlanchedAlmond")
                .drawRect(0, 0, this.gridSize, this.gridSize)
                .setStrokeStyle(1).beginStroke("#000")
                .moveTo(0, this.gridSize / 2).lineTo(this.gridSize, this.gridSize / 2)
                .moveTo(this.gridSize / 2, 0).lineTo(this.gridSize / 2, this.gridSize)
                .endStroke();
        }
        ,
        checkPiece: function (piece) {
            piece.graphics
                .beginFill(this.pieceColor)
                .drawCircle(this.gridSize / 2, this.gridSize / 2, this.gridSize / 3);
            piece.isChecked = true;
        }
        ,
        precheckPiece: function (piece) {
            var crossSize = 10;
            piece.graphics
                .setStrokeStyle(2).beginStroke("red")
                .moveTo(this.gridSize / 2 - crossSize, this.gridSize / 2).lineTo(this.gridSize / 2 + crossSize, this.gridSize / 2)
                .moveTo(this.gridSize / 2, this.gridSize / 2 - crossSize).lineTo(this.gridSize / 2, this.gridSize / 2 + crossSize)
                .endStroke();
        }
    },
    ready: function () {
        this.initChessBoard();
    }
})

