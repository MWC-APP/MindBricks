package ch.inf.usi.mindbricks.ui.nav.home.city;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class IsometricCityView extends View {

    private List<CitySlot> slots = new ArrayList<>();
    private final Paint paintUnlocked = new Paint();
    private final Paint paintLocked = new Paint();
    private final Paint paintOutline = new Paint();

    public IsometricCityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintUnlocked.setColor(Color.GREEN);
        paintLocked.setColor(Color.RED);
        paintOutline.setColor(Color.BLACK);
        paintOutline.setStyle(Paint.Style.STROKE);
        paintOutline.setStrokeWidth(4f);
    }

    public void setSlots(List<CitySlot> slots) {
        this.slots = slots;
        invalidate(); // redraw the view
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (slots == null || slots.isEmpty()) return;

        int maxCol = 0, maxRow = 0;
        for (CitySlot slot : slots) {
            if (slot.getCol() > maxCol) maxCol = slot.getCol();
            if (slot.getRow() > maxRow) maxRow = slot.getRow();
        }

        float cellWidth = getWidth() / (float) (maxCol + maxRow + 2);
        float cellHeight = getHeight() / (float) (maxCol + maxRow + 2);

        float scaleFactor = 1.8f;
        cellWidth *= scaleFactor;
        cellHeight *= scaleFactor;

        float yOffset = getHeight() / 6f;

        for (CitySlot slot : slots) {
            float isoX = (slot.getCol() - slot.getRow()) * cellWidth / 2 + getWidth() / 2f;
            float isoY = (slot.getCol() + slot.getRow()) * cellHeight / 2 + yOffset;

            float halfWidth = cellWidth / 2f;
            float halfHeight = cellHeight / 2f;

            // Diamond vertices
            float[] verts = {
                    isoX, isoY - halfHeight,  // top
                    isoX + halfWidth, isoY,    // right
                    isoX, isoY + halfHeight,   // bottom
                    isoX - halfWidth, isoY     // left
            };

            // Draw filled diamond
            Paint fillPaint = slot.isUnlocked() ? paintUnlocked : paintLocked;
            canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, verts.length, verts, 0,
                    null, 0, null, 0, null, 0, 0, fillPaint);

            // Draw outline
            canvas.drawLine(verts[0], verts[1], verts[2], verts[3], paintOutline);
            canvas.drawLine(verts[2], verts[3], verts[4], verts[5], paintOutline);
            canvas.drawLine(verts[4], verts[5], verts[6], verts[7], paintOutline);
            canvas.drawLine(verts[6], verts[7], verts[0], verts[1], paintOutline);
        }
    }
}
