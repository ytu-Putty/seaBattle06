import kotlin.random.Random
fun main() {
    val player = Player("Вы")
    val enemy = Player("Компьютер")
    printRules()
    println("=== РАССТАНОВКА ВАШИХ КОРАБЛЕЙ ===")
    placePlayerShips(player)
    println()
    println("--- КОМПЬЮТЕР РАССТАВЛЯЕТ КОРАБЛИ ---")
    placeEnemyShips(enemy)
    println()
    println("--- ИГРА НАЧАЛАСЬ ---")
    var playerTurn = true
    var countTurns = 0
    while (player.hasShips() && enemy.hasShips()) {
        println("=== У игрока ${player.name} осталось кораблей: ${player.countShips()} ===         === У игрока ${enemy.name} осталось кораблей: ${enemy.countShips()} ===")
        printBothFields(player, enemy)
        println()
        println("Потоплено вами кораблей компьютера: ${enemy.countSunkenShips()} из 10")
        println("Потоплено компьютером ваших кораблей: ${player.countSunkenShips()} из 10")
        println()
        if (playerTurn) {
            println("Ваш ход. Введите координаты выстрела (row col):")
            val inputLine = readln().trim()
            if (inputLine.lowercase() == "quit") {
                println("Вы вышли из игры")
                return
            }

            val input = inputLine.split(" ")
            if (input.size != 2) {
                println("Ошибка: нужно два числа через пробел.")
                continue
            }
            val r = input[0].toIntOrNull()
            val c = input[1].toIntOrNull()
            if (r == null || c == null) {
                println("Ошибка: координаты должны быть числами.")
                continue
            }
            if (!enemy.isInside(r, c)) {
                println("Ошибка: выстрел за границы поля (координаты от 0 до 9)!")
                continue
            }
            val targetState = enemy.field()[r][c]
            if (targetState == CellState.HIT || targetState == CellState.MISS) {
                println("Ошибка: вы сюда уже стреляли!")
                continue
            }
            countTurns++
            val hit = enemy.shoot(r, c)
            if (hit) {
                println("Попадание!")
            } else {
                println("Промах.")
                playerTurn = false
            }
        } else {
            println("Ход компьютера...")
            var r: Int
            var c: Int
            do {
                r = Random.nextInt(0, player.field().size - 1)
                c = Random.nextInt(0, player.field().size - 1)
            } while (player.field()[r][c] == CellState.HIT || player.field()[r][c] == CellState.MISS)
            countTurns++
            val hit = player.shoot(r, c)
            if (hit) {
                println("Компьютер попал в ($r, $c)!")
                playerTurn = false
            } else {
                println("Компьютер промахнулся в ($r, $c).")
                playerTurn = true
            }
        }
        println()
    }
    if (enemy.hasShips()) {
        println("Победил компьютер!")
    } else {
        println("Вы победили!")
    }
    println("Игра длилась $countTurns ходов.")
}








