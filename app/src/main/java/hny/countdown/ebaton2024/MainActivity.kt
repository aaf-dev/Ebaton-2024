package hny.countdown.ebaton2024

import android.os.Bundle as Пучок
import androidx.activity.ComponentActivity as КомпонентАктивность
import androidx.activity.compose.setContent as установитьСодержимое
import androidx.compose.animation.animateColor as анимированныйЦвет
import androidx.compose.animation.core.Animatable as Анимируемый
import androidx.compose.animation.core.LinearEasing as ЛинейноеСмягчение
import androidx.compose.animation.core.RepeatMode.Reverse as Наоборот
import androidx.compose.animation.core.StartOffset as НачальноеСмещение
import androidx.compose.animation.core.animateValue as анимироватьЗначение
import androidx.compose.animation.core.infiniteRepeatable as бесконечноПовторяющийся
import androidx.compose.animation.core.rememberInfiniteTransition as запомнитьБесконечныйПереход
import androidx.compose.animation.core.tween as подросток
import androidx.compose.foundation.Image as Картинка
import androidx.compose.foundation.background as фон
import androidx.compose.foundation.layout.Box as Коробка
import androidx.compose.foundation.layout.Column as Колонка
import androidx.compose.foundation.layout.fillMaxSize as заполнитьМаксимальныйРазмер
import androidx.compose.foundation.layout.fillMaxWidth as заполнитьМаксимальнуюШирину
import androidx.compose.foundation.layout.offset as смещение
import androidx.compose.foundation.layout.padding as отступ
import androidx.compose.foundation.layout.width as ширина
import androidx.compose.foundation.layout.wrapContentSize as обернутьРазмерСодержимого
import androidx.compose.material3.MaterialTheme as МатериальнаяТема
import androidx.compose.material3.MaterialTheme.colorScheme as цветоваяСхема
import androidx.compose.material3.Surface as Поверхность
import androidx.compose.material3.Text as Текст
import androidx.compose.runtime.Composable as Сборный
import androidx.compose.runtime.LaunchedEffect as ЭффектЗапуска
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf as изменяемоеСостояниеПлавающего
import androidx.compose.runtime.mutableStateOf as изменяемоеСостояниеЧегоТо
import androidx.compose.runtime.remember as запомнить
import androidx.compose.runtime.rememberCoroutineScope as запомнитьОбъемСопрограммы
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment as Выравнивание
import androidx.compose.ui.Modifier as Модификатор
import androidx.compose.ui.draw.rotate as вращение
import androidx.compose.ui.draw.scale as масштаб
import androidx.compose.ui.graphics.Color.Companion.White as Белый
import androidx.compose.ui.graphics.Color.Companion.Red as Красный
import androidx.compose.ui.graphics.Color.Companion.Black as Чёрный
import androidx.compose.ui.layout.ContentScale as МасштабированиеСодержимого
import androidx.compose.ui.res.painterResource as цветовойРесурс
import androidx.compose.ui.text.style.TextAlign as ВыравниваниеТекста
import androidx.compose.ui.unit.IntOffset as ДесятичноеСмещение
import androidx.compose.ui.unit.dp as пиксельЭкрана
import androidx.compose.ui.unit.sp as масштабируемыйПиксель
import kotlinx.coroutines.delay as задержка
import kotlinx.coroutines.launch as запустить
import hny.countdown.ebaton2024.R.drawable as картинка

private const val МАКСИМАЛЬНЫЙ_МАСШТАБ = 3F

class MainActivity : КомпонентАктивность() {

    override fun onCreate(savedInstanceState: Пучок?) {
        super.onCreate(savedInstanceState)
        установитьСодержимое {
            var данные by запомнить {
                изменяемоеСостояниеЧегоТо<ДанныеТаймера?>(null)
            }
            val локальныйОбъемСопрограммы = запомнитьОбъемСопрограммы()
            МатериальнаяТема {
                Поверхность(
                    modifier = Модификатор.заполнитьМаксимальныйРазмер(),
                    color = цветоваяСхема.background
                ) {
                    ЭффектЗапуска("timer") {
                        локальныйОбъемСопрограммы.запустить {
                            ТаймерПомощник.потокДанныхТаймера.collect { эти -> данные = эти }
                        }
                    }
                    if (данные != null) {
                        ЕбатонТаймер(данные!!)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ТаймерПомощник.запуск(this)
    }

    override fun onStop() {
        super.onStop()
        ТаймерПомощник.остановка()
    }
}

@Сборный
fun ЕбатонТаймер(данные: ДанныеТаймера) {
    val бесконечныйПереход = запомнитьБесконечныйПереход(label = "бесконечный переход")
    val масштаб by запомнить { изменяемоеСостояниеПлавающего(МАКСИМАЛЬНЫЙ_МАСШТАБ - данные.разницаДляМасштаба) }
    val анимированныйЦветФона by бесконечныйПереход.анимированныйЦвет(
        initialValue = Белый,
        targetValue = Красный,
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 500,
                easing = ЛинейноеСмягчение
            )
        ),
        label = "цвет"
    )
    val анимированныйЦветТекста by бесконечныйПереход.анимированныйЦвет(
        initialValue = Чёрный,
        targetValue = Белый,
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 500,
                easing = ЛинейноеСмягчение
            )
        ),
        label = "цвет"
    )
    val анимированнаяКапибараПоОсиИкс by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(0, 0),
        targetValue = ДесятичноеСмещение(-240, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            подросток(durationMillis = 1000, easing = ЛинейноеСмягчение),
            Наоборот
        ),
        label = "капибара x анимация"
    )
    val анимированныйДедМорозПоОсиИкс by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(0, 0),
        targetValue = ДесятичноеСмещение(240, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            подросток(durationMillis = 1000, easing = ЛинейноеСмягчение),
            Наоборот
        ),
        label = "дед мороз x анимация"
    )
    val анимированныеКапибараИДедМорозПоОсиИгрик by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(
            0,
            0
        ),
        targetValue = ДесятичноеСмещение(0, 120),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            подросток(durationMillis = 250, easing = ЛинейноеСмягчение),
            Наоборот
        ),
        label = "дед мороз и капибара y анимация"
    )
    val анимированныеВещи by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(0, 0),
        targetValue = ДесятичноеСмещение(0, 96),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            подросток(durationMillis = 2000, easing = ЛинейноеСмягчение),
            Наоборот
        ),
        label = "анимация вещей"
    )
    val анимированныеСнежинки by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(0, -800),
        targetValue = ДесятичноеСмещение(0, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 2000,
                easing = ЛинейноеСмягчение
            )
        ),
        label = "анимация снежинок"
    )
    val анимированныйЛевыйКот by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(-360, 360),
        targetValue = ДесятичноеСмещение(0, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 500,
                easing = ЛинейноеСмягчение,
                delayMillis = 2500
            ), Наоборот, НачальноеСмещение(2500)
        ),
        label = "левый кот"
    )
    val анимированныйПравыйКот by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(400, 400),
        targetValue = ДесятичноеСмещение(0, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 500,
                easing = ЛинейноеСмягчение,
                delayMillis = 2500
            ), Наоборот, НачальноеСмещение(5000)
        ),
        label = "правый кот"
    )
    val анимированныйКотБатон by бесконечныйПереход.анимироватьЗначение(
        initialValue = ДесятичноеСмещение(-600, 0),
        targetValue = ДесятичноеСмещение(-200, 0),
        typeConverter = ДесятичноеСмещениеТудаСюдаКонвертер(),
        animationSpec = бесконечноПовторяющийся(
            animation = подросток(
                durationMillis = 1000,
                easing = ЛинейноеСмягчение,
                delayMillis = 3000
            ), Наоборот, НачальноеСмещение(3000)
        ),
        label = "кот батон"
    )
    var угол by запомнить { изменяемоеСостояниеПлавающего(10F) }
    val объемСопрограммыАнимацииУгла = запомнитьОбъемСопрограммы()
    ЭффектЗапуска(key1 = "анимация угла") {
        объемСопрограммыАнимацииУгла.запустить {
            while (true) {
                задержка(1500)
                угол = -угол
            }
        }
    }
    val анимацияСмещенияЛетающихКотов = запомнить {
        Анимируемый(
            ДесятичноеСмещение(-1600, 0)
        )
    }
    ЭффектЗапуска(key1 = "летающие коты") {
        while (true) {
            анимацияСмещенияЛетающихКотов.animateTo(
                targetValue = ДесятичноеСмещение(1600, 200),
                animationSpec = подросток(1000, 500, ЛинейноеСмягчение)
            )
            анимацияСмещенияЛетающихКотов.animateTo(
                targetValue = ДесятичноеСмещение(-1600, 400),
                animationSpec = подросток(1000, 500, ЛинейноеСмягчение)
            )
            анимацияСмещенияЛетающихКотов.animateTo(
                targetValue = ДесятичноеСмещение(1600, 600),
                animationSpec = подросток(1000, 500, ЛинейноеСмягчение)
            )
            анимацияСмещенияЛетающихКотов.animateTo(
                targetValue = ДесятичноеСмещение(-1600, 800),
                animationSpec = подросток(1000, 500, ЛинейноеСмягчение)
            )
        }
    }
    Коробка(
        Модификатор
            .заполнитьМаксимальныйРазмер()
            .фон(анимированныйЦветФона)) {
        Картинка(
            painter = цветовойРесурс(id = картинка.pizzacat),
            contentDescription = null,
            contentScale = МасштабированиеСодержимого.Crop,
            modifier = Модификатор
                .отступ(top = 128.пиксельЭкрана)
                .обернутьРазмерСодержимого(unbounded = true)
                .масштаб(scaleX = масштаб, scaleY = масштаб)
                .align(Выравнивание.TopCenter)
                .вращение(угол)
                .смещение { анимацияСмещенияЛетающихКотов.value })
        Колонка(
            Модификатор
                .заполнитьМаксимальныйРазмер()
                .смещение { анимированныеСнежинки }) {
            Картинка(
                painter = цветовойРесурс(id = картинка.snowflakes),
                contentDescription = null,
                Модификатор.заполнитьМаксимальнуюШирину(),
                contentScale = МасштабированиеСодержимого.Crop,
                alpha = 0.5F
            )
            Картинка(
                painter = цветовойРесурс(id = картинка.snowflakes),
                contentDescription = null,
                Модификатор.заполнитьМаксимальнуюШирину(),
                contentScale = МасштабированиеСодержимого.Crop,
                alpha = 0.5F
            )
        }
        Картинка(
            painter = цветовойРесурс(id = картинка.capybara),
            contentDescription = "Капибара",
            Модификатор
                .масштаб(масштаб)
                .ширина(240.пиксельЭкрана)
                .align(Выравнивание.BottomCenter)
                .смещение(x = (-120).пиксельЭкрана, y = 40.пиксельЭкрана)
                .смещение { анимированныеКапибараИДедМорозПоОсиИгрик }
                .смещение { анимированнаяКапибараПоОсиИкс })
        Картинка(
            painter = цветовойРесурс(id = картинка.mandarin),
            contentDescription = "Мандарин",
            Модификатор
                .масштаб(масштаб)
                .ширина(240.пиксельЭкрана)
                .align(Выравнивание.BottomStart)
                .смещение(x = 20.пиксельЭкрана, y = 40.пиксельЭкрана)
                .смещение { анимированныеВещи })
        Картинка(
            painter = цветовойРесурс(id = картинка.santa),
            contentDescription = "Дед мороз радуется",
            Модификатор
                .масштаб(масштаб)
                .ширина(240.пиксельЭкрана)
                .align(Выравнивание.BottomCenter)
                .смещение(x = 60.пиксельЭкрана, y = 40.пиксельЭкрана)
                .смещение { анимированныеКапибараИДедМорозПоОсиИгрик }
                .смещение { анимированныйДедМорозПоОсиИкс })
        Картинка(
            painter = цветовойРесурс(id = картинка.vodka),
            contentDescription = "Водка",
            Модификатор
                .масштаб(масштаб)
                .ширина(240.пиксельЭкрана)
                .align(Выравнивание.BottomEnd)
                .смещение(x = 40.пиксельЭкрана, y = 40.пиксельЭкрана)
                .смещение { анимированныеВещи })
        Картинка(
            painter = цветовойРесурс(id = картинка.olivie),
            contentDescription = "Оливьешка",
            Модификатор
                .масштаб(масштаб)
                .ширина(240.пиксельЭкрана)
                .align(Выравнивание.BottomCenter)
                .смещение(x = 80.пиксельЭкрана, y = 40.пиксельЭкрана)
                .смещение { анимированныеВещи })
        Картинка(
            painter = цветовойРесурс(id = картинка.batoncat),
            contentDescription = "Полукот полубатон",
            Модификатор
                .масштаб(масштаб)
                .align(Выравнивание.CenterStart)
                .смещение { анимированныйКотБатон })
        Картинка(
            painter = цветовойРесурс(id = картинка.christmas_top),
            contentDescription = "Веночки",
            Модификатор
                .заполнитьМаксимальнуюШирину()
                .align(Выравнивание.TopCenter)
                .смещение(y = -(8).пиксельЭкрана),
            contentScale = МасштабированиеСодержимого.Crop
        )
        Картинка(
            painter = цветовойРесурс(id = картинка.christmas_bottom),
            contentDescription = "Ёлочки",
            Модификатор
                .заполнитьМаксимальнуюШирину()
                .align(Выравнивание.BottomCenter),
            contentScale = МасштабированиеСодержимого.Crop
        )
        Картинка(
            painter = цветовойРесурс(id = картинка.coolcat),
            contentDescription = "Крутой кот",
            Модификатор
                .масштаб(масштаб)
                .align(Выравнивание.BottomStart)
                .смещение { анимированныйЛевыйКот })
        Картинка(
            painter = цветовойРесурс(id = картинка.smilecat),
            contentDescription = "Кот улыбается",
            Модификатор
                .масштаб(масштаб)
                .align(Выравнивание.BottomEnd)
                .смещение { анимированныйПравыйКот })
        Колонка(
            Модификатор
                .align(Выравнивание.Center)
                .смещение(y = (-120).пиксельЭкрана)
        ) {
            val дни = данные.дни
            val часы = данные.часы
            val минуты = данные.минуты
            val секунды = данные.секунды
            if (дни > 0) {
                val шаблон = when {
                    дни in 5..20 -> "%d дней"
                    дни % 10 == 1 -> "%d день"
                    дни % 10 in 2..4 -> "%d дня"
                    else -> "%d дней"
                }
                val форматированныеДни = String.format(шаблон, дни)
                Текст(
                    text = форматированныеДни,
                    modifier = Модификатор
                        .отступ(8.пиксельЭкрана)
                        .заполнитьМаксимальнуюШирину(),
                    textAlign = ВыравниваниеТекста.Center,
                    fontSize = 32.масштабируемыйПиксель,
                    color = анимированныйЦветТекста
                )
            }
            if (часы > 0 || дни > 0) {
                val шаблон = when {
                    часы in 5..20 -> "%d часов"
                    часы % 10 == 1 -> "%d час"
                    часы % 10 in 2..4 -> "%d часа"
                    else -> "%d часов"
                }
                val форматированныеЧасы = String.format(шаблон, часы)
                Текст(
                    text = форматированныеЧасы,
                    modifier = Модификатор
                        .отступ(8.пиксельЭкрана)
                        .заполнитьМаксимальнуюШирину(),
                    textAlign = ВыравниваниеТекста.Center,
                    fontSize = 32.масштабируемыйПиксель,
                    color = анимированныйЦветТекста
                )
            }
            if (минуты > 0 || (дни > 0 || часы > 0)) {
                val шаблон = when {
                    минуты in 5..20 -> "%d минут"
                    минуты % 10 == 1 -> "%d минута"
                    минуты % 10 in 2..4 -> "%d минуты"
                    else -> "%d минут"
                }
                val форматированныеМинуты = String.format(шаблон, минуты)
                Текст(
                    text = форматированныеМинуты,
                    Модификатор
                        .отступ(8.пиксельЭкрана)
                        .заполнитьМаксимальнуюШирину(),
                    textAlign = ВыравниваниеТекста.Center,
                    fontSize = 32.масштабируемыйПиксель,
                    color = анимированныйЦветТекста
                )
            }
            if (секунды > 0 || (дни > 0 || часы > 0 || минуты > 0)) {
                val шаблон = when {
                    секунды in 5..20 -> "%d секунд"
                    секунды % 10 == 1 -> "%d секунда"
                    секунды % 10 in 2..4 -> "%d секунды"
                    else -> "%d секунд"
                }
                val форматированныеСекунды = String.format(шаблон, секунды)
                Текст(
                    text = форматированныеСекунды,
                    Модификатор
                        .отступ(8.пиксельЭкрана)
                        .заполнитьМаксимальнуюШирину(),
                    textAlign = ВыравниваниеТекста.Center,
                    fontSize = 32.масштабируемыйПиксель,
                    color = анимированныйЦветТекста
                )
            }
        }
    }
}

data class ДанныеТаймера(
    val дни: Int,
    val часы: Int,
    val минуты: Int,
    val секунды: Int,
    val разницаДляМасштаба: Float
)

fun Анимируемый(начальноеЗначение: ДесятичноеСмещение) =
    Анимируемый(начальноеЗначение, ДесятичноеСмещениеТудаСюдаКонвертер(), null)