package org.lifesparktech.lt_internal

import kotlinx.serialization.Serializable
import kotlin.collections.toMap

@Serializable
data class Payment
(
    val amount: Long,
    val base_amount: Long,
    val contact: String,
    val created_at: Long,
    val description: String?,
    val email: String,
    val fee: Long,
    val international: Boolean,
    val invoice_id: String?,
    val method: String,
    val refund_status: String?,
    val reward: String?,
    val status: String,
    val tax: Long,
    val order_id: String? = null,
){
    companion object {
        fun payment(it: Map<String?, Any?>): Payment {
            var x = it.toMap()
            val payment = Payment(
                amount = x["amount"] as Long,
                base_amount = x["base_amount"] as Long,
                contact = x["contact"] as String,
                created_at = x["created_at"] as Long,
                description = x["description"] as String?,
                email = x["email"] as String,
                fee = x["fee"] as Long,
                international = x["international"] as Boolean,
                invoice_id = x["invoice_id"] as String?,
                method = x["method"] as String,
                refund_status = x["refund_status"] as String?,
                reward = x["reward"] as String?,
                status = x["status"] as String,
                tax = x["tax"] as Long,
                order_id = x["order_id"] as String?,
            )
            return payment
        }
    }
}
