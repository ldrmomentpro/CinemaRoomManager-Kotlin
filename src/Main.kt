import java.util.*

var currentIncome = 0

fun main() {
    val row = enterRow()
    val seat = enterSeats()
    val mutList2D = MutableList(row) { MutableList(seat) { "S " } }
    var choose = ""

    while (choose != "0") {
        println(
            """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent()
        )
        choose = readLine()!!
        when (choose) {
            "1" -> showTheSeats(mutList2D, row, seat)
            "2" -> buyTicket(mutList2D, row, seat)
            "3" -> onChooseStatistics(mutList2D)
        }
    }
}

fun onChooseStatistics(mutList: MutableList<MutableList<String>>) {
    var purchaseTickets = 0
    for (i in mutList.indices) {
        for (j in mutList[i]) {
            if (j == "B ") purchaseTickets++
        }
    }
    val countSeats = mutList.size * mutList[0].size
    val percentage = String.format(Locale.US, "%.2f", 100.0 * purchaseTickets.toDouble() / countSeats.toDouble())
    val totalIncome = totalPrice(mutList, countSeats)
    println("\nNumber of purchased tickets: $purchaseTickets\nPercentage: $percentage%\nCurrent income: $$currentIncome\nTotal income: $$totalIncome")
}

fun totalPrice(mutList: MutableList<MutableList<String>>, countSeats: Int): Int {
    return if (countSeats < 60) 10 * countSeats else {
        val front = mutList.size / 2
        val back = mutList.size - front
        (front * mutList[0].size * 10) + (back * mutList[0].size * 8)
    }
}

fun enterRow(): Int {
    println("Enter the number of rows:")
    return readLine()!!.toInt()
}

fun enterSeats(): Int {
    println("Enter the number of seats in each row:")
    return readLine()!!.toInt()
}

fun enterRowNumberForTicket(): Int {
    println("Enter a row number:")
    return readLine()!!.toInt()
}

fun enterSeatInRowForTicket(): Int {
    println("Enter a seat number in that row:")
    return readLine()!!.toInt()
}

fun showTheSeats(mutList: MutableList<MutableList<String>>, row: Int, seat: Int) {
    var countSeatsInRow = 0

    print("\nCinema:\n ")
    while (countSeatsInRow != seat) {
        countSeatsInRow++
        print(" $countSeatsInRow")
    }
    println()
    for (i in 0 until row) {
        print("${i + 1} ")
        for (j in 0 until seat) {
            print(mutList[i][j])
        }
        println()
    }
}

fun buyTicket(mutList: MutableList<MutableList<String>>, row: Int, seat: Int) {
    var rowTicket = enterRowNumberForTicket()
    var seatTicket = enterSeatInRowForTicket()
    while (rowTicket > row || seatTicket > seat) {
        println("Wrong input!")
        rowTicket = enterRowNumberForTicket()
        seatTicket = enterSeatInRowForTicket()
    }
    while (mutList[rowTicket - 1][seatTicket - 1] == "B ") {
        println("That ticket has already been purchased!")
        rowTicket = enterRowNumberForTicket()
        seatTicket = enterSeatInRowForTicket()
    }

    val tickets = row * seat
    val price: Int = if (tickets < 60) {
        10
    } else {
        if (row / 2 >= rowTicket) 10 else 8
    }
    println("Ticket price: $$price")

    currentIncome += price
    mutList[rowTicket - 1][seatTicket - 1] = "B "
}