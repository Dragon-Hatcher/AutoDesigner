package extensions

import java.awt.Dimension

fun Dimension.min(d: Dimension) = Dimension(d.width.coerceAtMost(this.width), d.height.coerceAtMost(this.height))
fun Dimension.max(d: Dimension) = Dimension(d.width.coerceAtLeast(this.width), d.height.coerceAtLeast(this.height))
