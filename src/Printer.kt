fun printRules() {
    println("=========================================")
    println("         МОРСКОЙ БОЙ – ПРАВИЛА")
    println("=========================================")
    println("Поле 10х10. Координаты: сначала строка (0-9), потом столбец (0-9).")
    println()
    println("Корабли, которые нужно расставить:")
    println("  1 корабль  х 4 палубы")
    println("  2 корабля  х 3 палубы")
    println("  3 корабля  х 2 палубы")
    println("  4 корабля  х 1 палуба")
    println("Итого: 10 кораблей, 20 палуб.")
    println()
    println("Правила расстановки:")
    println("  • Корабли не могут выходить за границы поля.")
    println("  • Корабли не могут пересекаться.")
    println("  • Корабли не могут касаться друг друга даже углами.")
    println()
    println("Как вводить корабль:")
    println("  1. Введите координату НАЧАЛЬНОЙ палубы: row col")
    println("     Например: 3 4 – строка 3, столбец 4.")
    println("  2. Введите тип корабля: 1, 2, 3 или 4 (число палуб).")
    println("  3. Введите направление:")
    println("     h – горизонтально вправо (→)")
    println("     v – вертикально вниз (↓)")
    println()
    println("Пример: корабль из 3 палуб, начало (2, 5), горизонтально.")
    println("Ввод: 2 5, затем 3, затем h.")
    println("Займёт клетки: (2,5), (2,6), (2,7).")
    println()
    println("После каждого корабля поле перерисовывается.")
    println("=========================================")
    println()
}

fun printField(player: Player, debug: Boolean = true, showShips: Boolean = true)
{
    println()
    if(debug) {
        val size = player.field().size
        println("===Поле игрока ${player.name} (debug)===")
        print("    |")
        for (c in 0..player.field().size - 1) {
            if( c > 9)
            {
                print("$c |")
            }
            else print(" $c |")
        }
        println()
        for (r in 0..player.field().size - 1) {
            print("    ")
            println("+---".repeat(size) + "+")
            if( r > 9) {
                print(" $r |")
            }
            else print(" $r  |")
            for (c in 0..player.field().size - 1) {
                val value = player.field()[r][c]
                val displayChar = if (value == CellState.SHIP && !showShips) CellState.EMPTY else value
                print(" ${displayChar.toChar()} |")
            }
            println()
        }
        print("    ")
        println("+---".repeat(size) + "+")
    }
    else {
        println("===Поле игрока ${player.name}===")
        print("   ")
        for (c in 0..player.field().size - 1) {
            print(" $c ")
        }
        println()
        for (r in 0..player.field().size - 1) {
            print(" $r ")
            for (c in 0..player.field().size - 1) {
                val value = player.field()[r][c]
                val displayChar = if (value == CellState.SHIP && !showShips) CellState.EMPTY else value
                print(" ${displayChar.toChar()} ")
            }
            println()
        }
    }

}

fun printBothFields(player: Player, enemy: Player, showEnemyShips: Boolean = false) {
    val playerSize = player.field().size
    val enemySize = enemy.field().size
    if (playerSize != enemySize) {
        println("Ошибка: невозможно вывести поля рядом, так как их размеры различаются.")
        return
    }
    val size = player.field().size
    println()
    println("===Поле игрока ${player.name} ===        ===Поле игрока ${enemy.name} ===")
    print("  ")
    for (c in 0..size - 1) print("$c ")
    print("     ")
    print("   ")
    for(c in 0..size - 1) print("$c ")
    println()
    for (r in 0..size - 1) {
        print("$r ")
        for (c in 0..size - 1) print("${player.field()[r][c].toChar()} ")
        print("      ")
        print("$r ")

        for (c in 0..size - 1) {
            val value = enemy.field()[r][c]
            val displayChar = if (value == CellState.SHIP && !showEnemyShips) CellState.EMPTY else value
            print("${displayChar.toChar()} ")
        }
        println()
    }
}

fun printFields(fields: Array<Array<Array<CellState>>>, titles: Array<String>, showShips: BooleanArray) {
    println()
    if (fields.size != titles.size || fields.size != showShips.size) {
        println("Ошибка: количество полей (${fields.size}), заголовков (${titles.size}) и флагов (${showShips.size}) не совпадает.")
        return
    }
    if (fields.isEmpty()) {
        println("Ошибка: передан пустой список полей.")
        return
    }
    val size = fields[0].size
    for (field in fields)
    {
        if (field.size != size) {
            println("Ошибка: размеры полей различаются.")
            return
        }
    }
    for (i in 0 .. fields.size-1) {
        print("=== ${titles[i]} ===")
        if (i < fields.size - 1) {
            print("       ")
        }
    }
    println()
    repeat(fields.size) {
        print("   ")
        for (c in 0 .. size - 1) {
            print("$c ")
        }
        print("   ")
    }
    println()
    for (r in 0 .. size - 1) {
        for (i in 0..fields.size - 1) {
            print (" $r ")
            for (c in 0 .. size - 1) {
                val value = fields[i][r][c]
                val displayChar = if (value == CellState.SHIP && !showShips[i]) CellState.EMPTY else value
                print("${displayChar.toChar()}  ")
            }
            print("   ")
        }
        println()
    }
}