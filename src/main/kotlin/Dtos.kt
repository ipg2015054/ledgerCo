import java.math.BigDecimal

data class Balance(
    val bankName: String,
    val borrowersName: String,
    val amountPaid: BigDecimal,
    val emisLeft: Int
)

data class LoanBalance(
    val amountPaid: BigDecimal,
    val emisLeft: Int
)