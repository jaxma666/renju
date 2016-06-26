function initGame() {

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
                this.finishCreateGame();
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
            updategamePlayRight: function (creator, joiner) {
                this.creator = creator;
                this.joiner = joiner;
            }
        }
    })

    var chessboardVue = new Vue({
        el: '#chessboardVue',
        data: {
            gridSize: 50,
            validateSize: 700,
            gapSize: gridSize / 2,
            boardSize: validateSize + gapSize * 2,
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
                var gridCount = validateSize / gridSize;
                for (var row = 0; row <= gridCount; ++row) {
                    for (var col = 0; col <= gridCount; ++col) {
                        var piece = new createjs.Shape();
                        resetPiece(piece);
                        piece.x = col * gridSize + gapSize - gridSize / 2;
                        piece.y = row * gridSize + gapSize - gridSize / 2;
                        piece.on("click", function (event) {
                            if (!event.target.isChecked) {
                                checkPiece(event.target);
                                stage.update();
                            }
                        });
                        piece.on("mouseover", function (event) {
                            if (!event.target.isChecked) {
                                precheckPiece(event.target);
                                stage.update();
                            }
                        });
                        piece.on("mouseout", function (event) {
                            if (!event.target.isChecked) {
                                resetPiece(event.target);
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
                    .drawRect(0, 0, gridSize, gridSize)
                    .setStrokeStyle(1).beginStroke("#000")
                    .moveTo(0, gridSize / 2).lineTo(gridSize, gridSize / 2)
                    .moveTo(gridSize / 2, 0).lineTo(gridSize / 2, gridSize)
                    .endStroke();
            }
            ,
            checkPiece: function (piece) {
                piece.graphics
                    .beginFill(pieceColor)
                    .drawCircle(gridSize / 2, gridSize / 2, gridSize / 3);
                piece.isChecked = true;
            }
            ,
            precheckPiece: function (piece) {
                var crossSize = 10;
                piece.graphics
                    .setStrokeStyle(2).beginStroke("red")
                    .moveTo(gridSize / 2 - crossSize, gridSize / 2).lineTo(gridSize / 2 + crossSize, gridSize / 2)
                    .moveTo(gridSize / 2, gridSize / 2 - crossSize).lineTo(gridSize / 2, gridSize / 2 + crossSize)
                    .endStroke();
            }
        },
        ready: function () {
            this.initChessBoard();
        }
    })
}
