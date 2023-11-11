fun main(args: Array<String>) {
//    val inputs = listOf(
//        listOf("LOAN", "IDIDI", "Dale", "10000", "5", "4"),
//        listOf("LOAN", "MBI", "Harry", "2000", "2", "2"),
//        listOf("BALANCE", "IDIDI", "Dale", "5"),
//        listOf("BALANCE", "IDIDI", "Dale", "40"),
//        listOf("BALANCE", "MBI", "Harry", "12"),
//        listOf("BALANCE", "MBI", "Harry", "0")
//    )

    val inputs = listOf(
        listOf("LOAN", "IDIDI", "Dale", "5000", "1", "6"),
        listOf("LOAN", "MBI", "Harry", "10000", "3", "7"),
        listOf("LOAN", "UON", "Shelly", "15000", "2", "9"),
        listOf("PAYMENT", "IDIDI", "Dale", "1000", "5"),
        listOf("PAYMENT", "MBI", "Harry", "5000", "10"),
        listOf("PAYMENT", "UON", "Shelly", "7000", "12"),
        listOf("BALANCE", "IDIDI", "Dale", "3"),
        listOf("BALANCE", "IDIDI", "Dale", "6"),
        listOf("BALANCE", "UON", "Shelly", "12"),
        listOf("BALANCE", "MBI", "Harry", "12")
    )

    val ledger = Ledger()

    inputs.forEach {
        val queryType = it[0]

        when(queryType) {
            "LOAN" -> {
                val bankName = it[1]
                val borrowersName = it[2]
                val principal = it[3].toBigDecimal()
                val years = it[4].toInt()
                val rate = it[5].toBigDecimal()

                ledger.createLoan(bankName, borrowersName, principal, years, rate)
            }
            "BALANCE" -> {
                val bankName = it[1]
                val borrowersName = it[2]
                val emiNumber = it[3].toInt()

                val balance = ledger.getBalance(bankName, borrowersName, emiNumber)

                println(balance.bankName + " " + balance.borrowersName + " " + balance.amountPaid + " " + balance.emisLeft)

            }
            "PAYMENT" -> {
                val bankName = it[1]
                val borrowersName = it[2]
                val lumpsumAmount = it[3].toBigDecimal()
                val emiNumber = it[4].toInt()

                ledger.makePayment(bankName, borrowersName, lumpsumAmount, emiNumber)
            }
        }
    }
}