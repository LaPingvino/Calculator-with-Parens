(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
	'(java.math.BigDecimal))

(defn calculator-base []
  (let [frame (JFrame. "Calculator")
        value-1 (JTextField.)
	operator-selector (JButton. "?")
	value-2 (JTextField.)
	parens-1 (JButton. "( ... )")
	result (JTextField.)
	parens-2 (JButton. "( ... )")
	operate-on-1 (JButton. "Put result")
	get-result (JButton. "=")
	operate-on-2 (JButton. "Put result")]
     (.addActionListener get-result
      (proxy [ActionListener] []
        (actionPerformed [evt]
          (.setText result "Dit werkt"))))
    (doto frame
      (.setLayout (GridLayout. 3 3))
      (.add value-1)
      (.add operator-selector)
      (.add value-2)
      (.add operate-on-1)
      (.add result)
      (.add operate-on-2)
      (.add parens-1)
      (.add get-result)
      (.add parens-2)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setSize 450 120)
      (.setVisible true))))

(defn -main [& cl-args]
      (calculator-base))