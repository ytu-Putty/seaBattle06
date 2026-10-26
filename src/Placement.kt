import kotlin.random.Random

fun placePlayerShips(player: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    var index = 0
    while (index < plan.size) {
        val expectedSize = plan[index]
        println()
        println("Ставим корабль #${index + 1} из ${plan.size} (${expectedSize} палуб).")
        printField(player, false, true)

        println("Введите начальную координату (row col):")
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
        println("Введите тип корабля (1, 2, 3 или 4):")
        val size = readln().trim().toIntOrNull()
        if (size == null || size !in 1..4) {
            println("Ошибка: тип корабля должен быть 1, 2, 3 или 4.")
            continue
        }
        if (size != expectedSize) {
            println("Сейчас нужно ставить корабль из $expectedSize палуб, а не $size.")
            continue
        }
        val startRow = input[0].toIntOrNull()
        val startCol = input[1].toIntOrNull()

        if (startRow == null || startCol == null) {
            println("Ошибка: координаты должны быть числами.")
            continue
        }
        println("Введите направление (h – вправо, v – вниз):")
        val dir = readln().trim().lowercase()
        if (dir != "h" && dir != "v") {
            println("Ошибка: направление должно быть h или v.")
            continue
        }
        val horizontal = (dir == "h")
        val ok = player.placeShip(startRow, startCol, expectedSize, horizontal)
        if (ok) {
            println("Корабль поставлен!")
            index++
        } else {
            println("Нельзя поставить корабль здесь. Попробуйте снова.")
        }
    }
    println()
    println("Все корабли расставлены!")
    printField(player, false, true)
}

fun placeEnemyShips(enemy: Player) {
    val plan = listOf(4, 3, 3, 2, 2, 2, 1, 1, 1, 1)
    val maxAttempts = 1000
    var attempt = 0
    var index = 0
    while (index < plan.size) {
        val size = plan[index]
        var placed = false
        val cells = mutableListOf<Pair<Int, Int>>()
        for (r in 0 .. enemy.field().size)
        {
            for (c in 0 .. enemy.field().size)
            {
                cells.add(Pair(r,c))
            }
        }
        cells.shuffle()
        val startRow = Random.nextInt(0, 10)
        val startCol = Random.nextInt(0, 10)
        val horizontal = Random.nextBoolean()
        val ok = enemy.placeShip(startRow, startCol, size, horizontal)
        if (ok) {
            index++
            attempt = 0
        } else {
            attempt++
            if (attempt > maxAttempts) {
                println("Компьютер не смог расставить корабли. Сбрасываем поле...")
                enemy.reset()
                index = 0
                attempt = 0
            }
        }
    }
    println("Компьютер расставил корабли.")
}