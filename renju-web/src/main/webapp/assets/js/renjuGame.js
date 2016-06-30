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
            chessboard.changeColor();
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
            var simpleProtocol = {};
            simpleProtocol.action = 'create_game';
            sendMessage(JSON.stringify(simpleProtocol));
        },

        finishCreateGame: function (index) {
            $("#gameListArea").hide();
            $("#gamePlayArea").show();
            gamePlayRightVue.updategamePlayRight(index);
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
        updategamePlayRight: function (index) {
            var self = this;
            $.getJSON("/getGameInfo?index=" + index, function (data) {
                if (data.success) {
                    self.creator = data.result.creator;
                    self.joiner = data.result.joiner;
                }
            });
        }
    }
})


// 棋盘类
// 棋盘格子尺寸
// 本方颜色
function Chessboard(gridSize, onPieceChecked) {
    /** 私有成员 **/

    var validateSize = 700;
    var gapSize = gridSize / 2;
    var boardSize = validateSize + gapSize * 2;
    var gridCount = validateSize / gridSize;
    var locationMap = new Array();
    //创建者默认为黑棋
    var selfColor = "#000";

    /** 私有方法 **/

    function resetPiece(piece) {
        piece.graphics
            .beginFill("BlanchedAlmond")
            .drawRect(0, 0, gridSize, gridSize)
            .setStrokeStyle(1).beginStroke("#000")
            .moveTo(0, gridSize / 2).lineTo(gridSize, gridSize / 2)
            .moveTo(gridSize / 2, 0).lineTo(gridSize / 2, gridSize)
            .endStroke();
    }

    function checkPiece(piece, pieceColor) {
        piece.graphics
            .beginFill(pieceColor)
            .drawCircle(gridSize / 2, gridSize / 2, gridSize / 3);
        piece.isChecked = true;
    }

    function precheckPiece(piece) {
        var crossSize = 10;
        piece.graphics
            .setStrokeStyle(2).beginStroke("red")
            .moveTo(gridSize / 2 - crossSize, gridSize / 2).lineTo(gridSize / 2 + crossSize, gridSize / 2)
            .moveTo(gridSize / 2, gridSize / 2 - crossSize).lineTo(gridSize / 2, gridSize / 2 + crossSize)
            .endStroke();
    }


    /** 公有成员 **/

    var stage = new createjs.Stage("chessboardVue");
    stage.enableMouseOver();

    /** 公有方法 **/

    function setPiece(row, col, color) {
        if (color == "0") {
            color = "#000";
        } else {
            color = "#FFF";
        }
        checkPiece(locationMap[row][col], color);
        stage.update();
    }

    //挑战者默认为白棋
    function changeColor() {
        selfColor = "#FFF";
    }

    // var backgroud = new createjs.Shape();
    // backgroud.x = 0;
    // backgroud.y = 0;
    // backgroud.graphics
    //   .beginFill("BlanchedAlmond").drawRect(0, 0, boardSize, boardSize);

    // stage.addChild(backgroud);


    for (var row = 0; row <= gridCount; ++row) {

        locationMap[row] = new Array();

        for (var col = 0; col <= gridCount; ++col) {
            var piece = new createjs.Shape();

            locationMap[row][col] = piece;
            piece.row = row;
            piece.col = col;

            resetPiece(piece);
            piece.x = col * gridSize + gapSize - gridSize / 2;
            piece.y = row * gridSize + gapSize - gridSize / 2;

            piece.on("click", function (event) {
                if (!event.target.isChecked) {
                    checkPiece(event.target, selfColor);
                    onPieceChecked(event.target.row, event.target.col, selfColor);
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


    // 导出公有成员
    this.setPiece = setPiece;
    this.changeColor = changeColor;
    this.stage = stage;
}

var chessboard;


chessboard = new Chessboard(50, function (row, col, color) {
    var simpleProtocol = {};
    simpleProtocol.action = "do_step";
    var chessman = {};
    chessman.position = {};
    chessman.position.row = row;
    chessman.position.column = col;
    if (color == "#000") {
        chessman.color = 0;
    } else {
        chessman.color = 1;
    }
    simpleProtocol.content = chessman;
    sendMessage(JSON.stringify(simpleProtocol));
});


//
// var chessboardVue = new Vue({
//     el: '#chessboardVue',
//
//     methods: {
//         tryToSetPiece: function (row, col) {
//             var simpleProtocol = {};
//             simpleProtocol.action = "do_step";
//             var chessman = {};
//             chessman.position = {};
//             chessman.position.row = row / 50;
//             chessman.position.column = col / 50;
//             if (this.pieceColor == "#000") {
//                 chessman.color = 0;
//             } else {
//                 chessman.color = 1;
//             }
//             simpleProtocol.content = chessman;
//             sendMessage(JSON.stringify(simpleProtocol));
//         },
//         setPiece: function (row, col, color) {
//             chessboard.setPiece(row, col, color);
//         },
//         changeColor: function () {
//             chessboard.changeColor();
//         }
//
//
//     },
//     ready: function () {
//     }
// })
//
