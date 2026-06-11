package com.film.app.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat
import com.film.app.R
import kotlin.math.max
import kotlin.math.min

class SeatView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val seatPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    
    // Seat states: 0-Unavailable, 1-Available, 2-Sold, 3-Selected
    private var seatList: List<SeatData> = emptyList()
    private var selectedSeats: MutableSet<Long> = mutableSetOf()
    
    private var maxRow = 0
    private var maxCol = 0

    private var seatSize = 80f // Increased size
    private var seatGap = 15f  // Increased gap
    
    // Transformation
    private val matrix = Matrix()
    private val scaleDetector = ScaleGestureDetector(context, ScaleListener())
    private val gestureDetector = GestureDetector(context, GestureListener())
    
    private var scaleFactor = 1.0f
    private var focusX = 0f
    private var focusY = 0f

    data class SeatData(
        val id: Long,
        val row: Int,
        val col: Int,
        val status: Int, // 0-Unavailable, 1-Available, 2-Sold
        val label: String
    )

    fun setData(seats: List<SeatData>) {
        this.seatList = seats
        this.selectedSeats.clear()
        if (seats.isNotEmpty()) {
            maxRow = seats.maxOf { it.row }
            maxCol = seats.maxOf { it.col }
            
            // Calculate center
            val contentWidth = maxCol * (seatSize + seatGap) + seatGap
            val contentHeight = maxRow * (seatSize + seatGap) + seatGap
            
            post {
                val viewWidth = width.toFloat()
                val viewHeight = height.toFloat()
                
                if (viewWidth > 0 && viewHeight > 0) {
                    val dx = (viewWidth - contentWidth) / 2
                    val dy = (viewHeight - contentHeight) / 2
                    
                    matrix.reset()
                    matrix.postTranslate(dx, dy)
                    invalidate()
                }
            }
        }
        invalidate()
    }

    fun getSelectedSeatIds(): List<Long> {
        return selectedSeats.toList()
    }
    
    fun getSelectedSeatLabels(): List<String> {
        return seatList.filter { selectedSeats.contains(it.id) }.map { it.label }
    }

    init {
        seatPaint.style = Paint.Style.FILL
        textPaint.color = Color.WHITE
        textPaint.textSize = 20f
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.concat(matrix)

        for (seat in seatList) {
            // 1-based index to 0-based coordinate
            val r = seat.row - 1
            val c = seat.col - 1
            
            val left = c * (seatSize + seatGap) + seatGap
            val top = r * (seatSize + seatGap) + seatGap
            val right = left + seatSize
            val bottom = top + seatSize
            
            // Color based on status
            // Backend: 0-Unavailable, 1-Available, 2-Sold
            // View Logic: 3-Selected (Local state)
            
            if (selectedSeats.contains(seat.id)) {
                 seatPaint.color = Color.GREEN // Selected
            } else {
                when (seat.status) {
                    0 -> seatPaint.color = Color.TRANSPARENT // Unavailable/Broken (or draw X)
                    1 -> seatPaint.color = Color.WHITE   // Available
                    2 -> seatPaint.color = Color.RED     // Sold
                }
            }

            if (seat.status != 0) {
                val rect = RectF(left, top, right, bottom)
                canvas.drawRoundRect(rect, 8f, 8f, seatPaint)
            }
        }
        
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = max(0.5f, min(scaleFactor, 3.0f))
            
            matrix.postScale(detector.scaleFactor, detector.scaleFactor, detector.focusX, detector.focusY)
            invalidate()
            return true
        }
    }

    // Callback for selection change
    var onSeatSelectedListener: ((List<String>) -> Unit)? = null

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            matrix.postTranslate(-distanceX, -distanceY)
            invalidate()
            return true
        }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        // Calculate clicked seat
        val inverse = Matrix()
        matrix.invert(inverse)
        val touchPoint = floatArrayOf(e.x, e.y)
        inverse.mapPoints(touchPoint)
        
        val contentX = touchPoint[0]
        val contentY = touchPoint[1]
        
        // 0-based col/row index
        val col = ((contentX - seatGap) / (seatSize + seatGap)).toInt()
        val row = ((contentY - seatGap) / (seatSize + seatGap)).toInt()
        
        // Find seat in list (row/col are 1-based in data)
        val seat = seatList.find { it.row == row + 1 && it.col == col + 1 }
        
        if (seat != null) {
            if (seat.status == 1) { // Available
                if (selectedSeats.contains(seat.id)) {
                    selectedSeats.remove(seat.id)
                } else {
                    // Max 4 seats selection limit (optional)
                    if (selectedSeats.size < 4) {
                        selectedSeats.add(seat.id)
                    }
                }
                invalidate()
                // Notify listener
                onSeatSelectedListener?.invoke(getSelectedSeatLabels())
            }
        }
        
        return true
    }
    }
}
