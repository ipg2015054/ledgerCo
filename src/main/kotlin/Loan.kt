import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.ceil

class Loan(
    val amount: BigDecimal,
    val emi: Int
) {
    val lumpsumPayments = mutableListOf<Payment>()

    fun makePayment(payment: Payment) {
        lumpsumPayments.add(payment)
    }

    fun getBalance(emiNumber: Int): LoanBalance {
        val emiAmountPaid = emiNumber.toBigDecimal()*emi.toBigDecimal()
        val lumpsumAmountPaid = getLumpsumAmountPaid(emiNumber)

        val totalAmountPaid = emiAmountPaid + lumpsumAmountPaid

        val paymentLeft = if(amount - totalAmountPaid > BigDecimal.ZERO) {
            amount - totalAmountPaid
        } else {
            BigDecimal.ZERO
        }

        val emisLeft = ceil((paymentLeft.divide(emi.toBigDecimal(), 2, RoundingMode.CEILING)).toDouble()).toInt()

        return LoanBalance(totalAmountPaid, emisLeft)
    }

    private fun getLumpsumAmountPaid(emiNumber: Int): BigDecimal {
        return lumpsumPayments.filter { it.emiNo <= emiNumber }.sumOf { it.amount }
    }
}