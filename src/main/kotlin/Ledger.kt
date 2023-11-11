import java.lang.IllegalStateException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil

class Ledger {
    val loans = mutableMapOf<IdempotencyKey, Loan>()

    fun createLoan(bankName: String, borrowerName: String, principal: BigDecimal, years: Int, rate: BigDecimal) {
        val key = IdempotencyKey(bankName, borrowerName)

        if(loans[key] != null) {
            println("Can not create another loan as one loan is already present corresponding to this bank and borrower")
        }

        loans[key] = createLoan(principal, years, rate)
    }

    private fun createLoan(principal: BigDecimal, years: Int, rate: BigDecimal): Loan {
        val amount = principal + (principal*rate*years.toBigDecimal())/ BigDecimal("100")
        println("$amount  $principal  $years  $rate")
        val emi = ceil((amount.divide(years.toBigDecimal()*BigDecimal("12"), 2, RoundingMode.CEILING)).toDouble()).toInt()

        return Loan(amount, emi)
    }

    fun makePayment(bankName: String, borrowerName: String, lumpsumAmount: BigDecimal, emiNumber: Int) {
        val payment = Payment(emiNumber, lumpsumAmount)
        val loan = fetchLoan(bankName, borrowerName)

        loan.makePayment(payment)
    }

    fun getBalance(bankName: String, borrowerName: String, emiNumber: Int): Balance {
        val loan = fetchLoan(bankName, borrowerName)
        val loanBalance = loan.getBalance(emiNumber)

        return Balance(bankName, borrowerName, loanBalance.amountPaid, loanBalance.emisLeft)
    }

    private fun fetchLoan(bankName: String, borrowerName: String): Loan {
        val key = IdempotencyKey(bankName, borrowerName)

        return loans[key] ?: throw IllegalStateException("No loan present for $bankName and $borrowerName")
    }
}

data class IdempotencyKey(
    val bankName: String,
    val borrowerName: String
)