class Player(val name: String, size: Int = 10) {
    private var _field: Array<Array<CellState>> = Array(size) {Array(size) {CellState.EMPTY}}
    fun field(): Array<Array<CellState>> = _field
    fun placeShip(row: Int, col: Int) {
        if(row in 0.._field.size - 1 && col in 0.._field.size - 1) {
            _field[row][col] = CellState.SHIP
        }
    }
    private var _shots: Int = 0
    val shots: Int
        get() = _shots

    private var _hits: Int = 0
    val hits: Int
        get() = _hits

    var shipsLeft: Int = 20
    fun isAlive(): Boolean {
        return shipsLeft > 0
    }
    fun accuracy(): Double {
        if(_shots == 0) return 0.0
        else return  _hits * 100.0 / _shots
    }
    fun printStats() {
        println("Игрок $name: уцелело кораблей: ${countShips()}, потоплено врагом: ${countSunkenShips()}, выстрелов: $_shots, попаданий: $_hits, точность: ${String.format("%.1f", accuracy())}%")
    }
    fun status(){
        printStats()
    }
    fun takeDamage(){
        shipsLeft -= 1
        isAlive()
    }
    fun registerShot(hit: Boolean){
        _shots++
        if (hit) _hits++
    }
    fun markShot(row: Int, col: Int, hit: Boolean){
        if(row in 0.._field.size - 1 && col in 0.._field.size - 1) {
            _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        }
    }
    fun isInside(row: Int, col: Int): Boolean {
        return row in _field.indices && col in _field.indices
    }

    fun hasShips(): Boolean {
        for (row in 0 until  _field.size) {
            for (col in 0 until _field.size) {
                if (_field[row][col] == CellState.SHIP) return true
            }
        }
        return false
    }

    fun countShips(): Int {
        var count = 0
        val size = _field.size
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (_field[r][c] == CellState.SHIP) {
                    val hasShipLeft = c > 0 && _field[r][c - 1] == CellState.SHIP
                    val hasShipAbove = r > 0 && _field[r - 1][c] == CellState.SHIP
                    if (!hasShipLeft && !hasShipAbove) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun countSunkenShips(): Int {
        return 10 - countShips()
    }

    fun canPlaceShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        for(i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal)  startCol + i else startCol
            if (!isInside(r, c)) return false
            if(_field[r][c] != CellState.EMPTY) return false
            for (dr in -1 ..1) {
                for (dc in -1 ..1) {
                    if (dr == 0 && dc == 0) continue
                    val nr = r + dr
                    val nc = c + dc
                    if (isInside(nr, nc) && _field[nr][nc] == CellState.SHIP) return false
                }
            }
        }
        return true
    }
    fun placeShip(startRow: Int, startCol: Int, size: Int, horizontal: Boolean): Boolean {
        if (!canPlaceShip(startRow, startCol, size, horizontal)) {
            return false
        }
        for (i in 0 until size) {
            val r = if (horizontal) startRow else startRow + i
            val c = if (horizontal) startCol + i else startCol
            _field[r][c] = CellState.SHIP
        }
        return true
    }

    fun canShootAt(row: Int, col:Int): Boolean{
        if (!isInside(row,col)) return false
        val state = _field[row][col]
        return state != CellState.HIT && state != CellState.MISS
    }

    fun shoot(row: Int, col: Int): Boolean {
        if (!canShootAt(row,col)){
            println("Выстрел невозможен: за границей или сюда уже стреляли.")
            return false
        }
        val hit = (_field[row][col] == CellState.SHIP)
        _field[row][col] = if (hit) CellState.HIT else CellState.MISS
        registerShot(hit)
        return hit
    }
    fun reset(){
        _shots = 0
        _hits = 0
        shipsLeft = 20
        for (r in 0 .. _field.size-1) {
            for (c in 0 .. _field[0].size-1) {
                _field[r][c] = CellState.EMPTY
            }
        }
    }
}