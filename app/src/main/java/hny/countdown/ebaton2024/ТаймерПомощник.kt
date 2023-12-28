package hny.countdown.ebaton2024

import android.content.Context as БожественныйОбъект
import android.media.MediaPlayer as Проигрыватель
import java.util.Calendar as Календарь
import java.util.Date as Дата
import kotlinx.coroutines.CoroutineScope as ОбъемСопрограммы
import kotlinx.coroutines.Dispatchers as Диспетчер
import kotlinx.coroutines.Job as Работа
import kotlinx.coroutines.cancel as отмена
import kotlinx.coroutines.delay as задержка
import kotlinx.coroutines.flow.MutableSharedFlow as ИзменяемыйОбщийПоток
import kotlinx.coroutines.launch as запуск

private const val СЕКУНДА = 1000
private const val МИНУТА = СЕКУНДА * 60
private const val ЧАС = МИНУТА * 60
private const val ДЕНЬ = ЧАС * 24

object ТаймерПомощник {
    val потокДанныхТаймера: ИзменяемыйОбщийПоток<ДанныеТаймера> = ИзменяемыйОбщийПоток()
    private var объем: ОбъемСопрограммы? = null
    private var работаТаймера: Работа? = null
    private var проигрыватель: Проигрыватель? = null
    private var начатЛи: Boolean = false
    private val начало2024ГодаВМилисекундах =
        Календарь.getInstance().apply { set(2024, 0, 1, 0, 0, 0) }.time.time

    fun запуск(божественныйОбъект: БожественныйОбъект) {
        запускТаймера()
        запускПроигрывателя(божественныйОбъект)
    }
    fun остановка() {
        остановкаТаймера()
        остановкаПроигрывателя()
    }
    private fun запускПроигрывателя(божественныйОбъект: БожественныйОбъект) {
        val файловыйМенеджер = божественныйОбъект.assets
        val названиеФайла = "xmas_ringtone.mp3"
        val описаниеФайла = файловыйМенеджер.openFd(названиеФайла)
        проигрыватель = Проигрыватель().apply {
            setDataSource(
                описаниеФайла.fileDescriptor,
                описаниеФайла.startOffset,
                описаниеФайла.length
            )
            prepare()
            start()
        }
        проигрыватель?.setOnCompletionListener { it.start() }
    }
    private fun остановкаПроигрывателя() {
        проигрыватель?.release()
        проигрыватель = null
    }
    private fun запускТаймера() {
        начатЛи = true
        объем = ОбъемСопрограммы(Диспетчер.IO)
        работаТаймера = объем?.запуск {
            while (начатЛи) {
                val результат = посчитатьОставшеесяВремя()
                потокДанныхТаймера.emit(результат)
                задержка(1000)
            }
        }
    }
    private fun остановкаТаймера() {
        начатЛи = false
        объем?.отмена()
        работаТаймера?.cancel()
    }
    private fun посчитатьОставшеесяВремя(): ДанныеТаймера {
        val текущееВремяВМиллисекундах = Дата().time
        val разница = начало2024ГодаВМилисекундах - текущееВремяВМиллисекундах
        val осталосьДней = разница / ДЕНЬ
        val осталосьЧасов = (разница - (осталосьДней * ДЕНЬ)) / ЧАС
        val осталосьМинут = (разница - (осталосьДней * ДЕНЬ + осталосьЧасов * ЧАС)) / МИНУТА
        val осталосьСекунд =
            (разница - (осталосьДней * ДЕНЬ + осталосьЧасов * ЧАС + осталосьМинут * МИНУТА)) / СЕКУНДА
        val разницаДляМасштаба = (разница / 500_000_000).toFloat()
        return ДанныеТаймера(
            осталосьДней.toInt(),
            осталосьЧасов.toInt(),
            осталосьМинут.toInt(),
            осталосьСекунд.toInt(),
            разницаДляМасштаба
        )
    }
}