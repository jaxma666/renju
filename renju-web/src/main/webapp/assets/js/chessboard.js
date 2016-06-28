// 导出API：设置棋子
// (row, col, color)
var onSetPiece;

function initChessBoard() {
    // 常量
    var gridSize = 50;
    var validateSize = 700;
    var gapSize = gridSize / 2;
    var boardSize = validateSize + gapSize * 2;

    // 本方颜色
    var selfColor = '#000';

    // 工具函数
    function resetPiece(piece) {
        piece.graphics
            .beginFill("BlanchedAlmond")
            .drawRect(0, 0, gridSize, gridSize)
            .setStrokeStyle(1).beginStroke("#000")
            .moveTo(0, gridSize / 2).lineTo(gridSize, gridSize / 2)
            .moveTo(gridSize / 2, 0).lineTo(gridSize / 2, gridSize)
            .endStroke();
    }

    // 工具函数
    function checkPiece(piece, pieceColor) {
        piece.graphics
            .beginFill(pieceColor)
            .drawCircle(gridSize / 2, gridSize / 2, gridSize / 3);
        piece.isChecked = true;
    }

    // 工具函数
    function precheckPiece(piece) {
        var crossSize = 10;
        piece.graphics
            .setStrokeStyle(2).beginStroke("red")
            .moveTo(gridSize / 2 - crossSize, gridSize / 2).lineTo(gridSize / 2 + crossSize, gridSize / 2)
            .moveTo(gridSize / 2, gridSize / 2 - crossSize).lineTo(gridSize / 2, gridSize / 2 + crossSize)
            .endStroke();
    }

    var stage = new createjs.Stage("chessboard");
    stage.enableMouseOver();

    // var backgroud = new createjs.Shape();
    // backgroud.x = 0;
    // backgroud.y = 0;
    // backgroud.graphics
    //   .beginFill("BlanchedAlmond").drawRect(0, 0, boardSize, boardSize);

    // stage.addChild(backgroud);

    var gridCount = validateSize / gridSize;


    var locationMap = new Array();

    function setPiece(row, col, color) {
        checkPiece(locationMap[row][col], color);
        stage.update();
    }

    // 导出
    onSetPiece = setPiece;

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

}