import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val amount: Long,
    val base_amount: Long,
    val contact: String,
    val created_at: Long,
    val description: String? = null,
    val email: String,
    val fee: Long,
    val international: Boolean,
    val invoice_id: String? = null,
    val method: String,
    val refund_status: String? = null,
    val reward: String? = null,
    val status: String,
    val tax: Long,
)
