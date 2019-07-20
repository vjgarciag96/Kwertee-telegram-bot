package domain

import model.TShirt

fun TShirt.toTextMessage(): String =
    title
        .plus("\n")
        .plus("Price:$eurPrice|$usdPrice|$gbpPrice")