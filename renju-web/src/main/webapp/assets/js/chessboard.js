var gridSize = 50;
var validateSize = 700;
var gapSize = gridSize / 2;
var boardSize = validateSize + gapSize * 2;
var pieceColor = "#000";

function resetPiece(piece) {
    piece.graphics
        .beginFill("BlanchedAlmond")
        .drawRect(0, 0, gridSize, gridSize)
        .setStrokeStyle(1).beginStroke("#000")
        .moveTo(0, gridSize / 2).lineTo(gridSize, gridSize / 2)
        .moveTo(gridSize / 2, 0).lineTo(gridSize / 2, gridSize)
        .endStroke();
}

function checkPiece(piece) {
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

function initChessBoard() {
    
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