(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
	'(java.math.BigDecimal))

(defn fac [x]
    (loop [n x f 1]
        (if (< n 1)
            f
            (recur (dec n) (* f n)))))

(defn calcform [from to converter]
  (let [frame (JFrame. (str "Convert " from " to " to))
        rootpane (.getRootPane frame)
	temp-text (JTextField.)
        from-label (JLabel. from)
        convert-button (JButton. "Convert")
        to-label (JLabel. (str "convert to " to))]
     (.setDefaultButton rootpane convert-button)
     (.addActionListener convert-button
      (proxy [ActionListener] []
        (actionPerformed [evt]
          (let [value (. BigDecimal valueOf (Double/parseDouble (.getText temp-text)))]
            (.setText to-label
               (str (converter value) to))))))
    (doto frame
      (.setLayout (GridLayout. 2 2 3 3))
      (.add temp-text)
      (.add from-label)
      (.add convert-button)
      (.add to-label)
      (.setSize 300 80)
      (.setVisible true))))

(defn converter-fn []
  (let [frame (JFrame. "Converter")
        c2f-button (JButton. "Convert °C to °F")
	f2c-button (JButton. "Convert °F to °C")
	fac-button (JButton. "Factorial")]
     (.addActionListener c2f-button
      (proxy [ActionListener] []
        (actionPerformed [evt]
          (calcform "°C" "°F" (fn [c] (+ 32 (* 9/5 c)))))))
     (.addActionListener f2c-button
      (proxy [ActionListener] []
        (actionPerformed [evt]
          (calcform "°F" "°C" (fn [f] (* (- f 32) 5/9))))))
     (.addActionListener fac-button
      (proxy [ActionListener] []
        (actionPerformed [evt]
          (calcform "" "!" fac))))
    (doto frame
      (.setLayout (GridLayout. 3 1))
      (.add c2f-button)
      (.add f2c-button)
      (.add fac-button)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setSize 300 100)
      (.setVisible true))))

(defn calculator-base []
  (let [frame (JFrame. "Calculator")
        value-1 (JTextField.)
	operator-selector (JButton. "?")
	value-2 (JTextField.)
	parens-1 (JButton. "()")
	result (JTextField.)
	parens-2 (JButton. "()")
	operate-on-1 (JButton. "^")
	get-result (JButton. "=")
	operate-on-2 (JButton. "^")]
    (doto frame
      (.setLayout (GridLayout. 3 3))
      (.add value-1)
      (.add operator-selector)
      (.add value-2)
      (.add parens-1)
      (.add result)
      (.add parens-2)
      (.add operate-on-1)
      (.add get-result)
      (.add operate-on-2)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setSize 75 600)
      (.setVisible true))))

(defn -main [& cl-args]
      (calculator-base))