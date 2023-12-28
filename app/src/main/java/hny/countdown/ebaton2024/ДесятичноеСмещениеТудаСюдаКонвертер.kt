package hny.countdown.ebaton2024

import androidx.compose.animation.core.AnimationVector2D as ВекторнаяАнимация2Д
import androidx.compose.animation.core.TwoWayConverter as ТудаСюдаКонвертер
import androidx.compose.ui.unit.IntOffset as ДесятичноеСмещение

class ДесятичноеСмещениеТудаСюдаКонвертер : ТудаСюдаКонвертер<ДесятичноеСмещение, ВекторнаяАнимация2Д> {

    override val convertFromVector: (ВекторнаяАнимация2Д) -> ДесятичноеСмещение =
        { ДесятичноеСмещение(it.v1.toInt(), it.v2.toInt()) }

    override val convertToVector: (ДесятичноеСмещение) -> ВекторнаяАнимация2Д =
        { ВекторнаяАнимация2Д(it.x.toFloat(), it.y.toFloat()) }
}