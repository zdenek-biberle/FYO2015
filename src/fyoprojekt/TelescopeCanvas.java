/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fyoprojekt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 *
 * @author fast4shoot
 */
public class TelescopeCanvas extends javax.swing.JPanel {

    private Iterable<Element> elems;
    private Point raySource;
    
    /**
     * Creates new form TelescopeCanvas
     */
    public TelescopeCanvas() {
        initComponents();
    }

    public Iterable<Element> getElems() {
        return elems;
    }

    public void setElems(Iterable<Element> elems) {
        this.elems = elems;
        repaint();
    }

    public Point getRaySource() {
        return raySource;
    }

    public void setRaySource(Point raySource) {
        this.raySource = raySource;
        repaint();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        
        for (Element elem : elems)
        {
            elem.paint(g, getWidth(), getHeight());
        }
        
        Ray ray = new Ray(raySource, new Vector(1.0, 0.9), 1.0);
        paintRay(ray, g, Color.RED);/*
        ray = new Ray(raySource, new Vector(1.0, -0.9), 1.0);
        paintRay(ray, g, Color.BLUE);/*
        ray = new Ray(raySource, new Vector(-1.0, 0.9), 1.0);
        paintRay(ray, g, Color.GREEN);
        ray = new Ray(raySource, new Vector(-1.0, -0.9), 1.0);
        paintRay(ray, g, Color.MAGENTA);*/
    } 
    
    void paintRay(Ray ray, Graphics g, Color color)
    {
        Element hitElem = null;
        while(ray.getIntensity() > 0.0001)
        {
            Ray newRay = null;
            Element newHitElem = null;
            double minDist = Double.MAX_VALUE;
            for (Element elem : elems)
            {
                if (elem != hitElem)
                {
                    Ray candidateRay = elem.testHit(ray);
                    if (candidateRay != null)
                    {
                        if (new Vector(ray.getPoint(), candidateRay.getPoint()).length() < minDist)
                        {
                            newRay = candidateRay;
                            newHitElem = elem;
                        }
                    }
                }
            }
            
            if (newRay != null)
            {
                g.setColor(color);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)Math.round(color.getAlpha() * ray.getIntensity())));
                g.drawLine(
                    (int)Math.round(ray.getPoint().x() * getWidth()),
                    (int)Math.round(ray.getPoint().y() * getHeight()),
                    (int)Math.round(newRay.getPoint().x() * getWidth()),
                    (int)Math.round(newRay.getPoint().y() * getHeight()));
                ray = newRay;
                hitElem = newHitElem;
            }
            else
            {
                break;
            }
        }
        
        Point lastPoint = ray.getDirection().add(ray.getPoint());
        g.drawLine(
            (int)Math.round(ray.getPoint().x() * getWidth()),
            (int)Math.round(ray.getPoint().y() * getHeight()),
            (int)Math.round(lastPoint.x() * getWidth()),
            (int)Math.round(lastPoint.y() * getHeight()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
