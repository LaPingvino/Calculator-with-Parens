(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(java.math.BigDecimal))

(defn calculate []
  (let [frame (JFrame. "Calculator")
    ; Elements ordered by appearance on the calculator
    value-1 (JTextField.) operator-selector (JButton. "?") value-2 (JTextField.)
    parens-1 (JButton. "( ... )") result (JTextField.) parens-2 (JButton. "( ... )")
    operate-on-1 (JButton. "Put result") get-result (JButton. "=") operate-on-2 (JButton. "Put result")
    result-value (agent 0)
    operator *]
    
    ; Action listeners to Get Things Done
    (.addActionListener get-result
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (if (= (.getText result) "") (.setText result (str (operator (Double/parseDouble (.getText value-1))
                                                                                                             (Double/parseDouble (.getText value-2)))))
                                                  (send resultvalue (fn [x] x) (.getText result))))))
    
    ; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 3 3))
          (.add value-1) (.add operator-selector) (.add value-2)
          (.add operate-on-1) (.add result) (.add operate-on-2)
          (.add parens-1) (.add get-result) (.add parens-2)
          (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
          (.setSize 450 120)
          (.setVisible true))
    (await result-value)))

  (defn calculator-base []
    (let [frame (JFrame. "Calculator")
      ; Elements ordered by appearance on the calculator
      value-1 (JTextField.) operator-selector (JButton. "?") value-2 (JTextField.)
      parens-1 (JButton. "( ... )") result (JTextField.) parens-2 (JButton. "( ... )")
      operate-on-1 (JButton. "Put result") get-result (JButton. "=") operate-on-2 (JButton. "Put result")
      operator *]
      
      ; Action listeners to Get Things Done
      (.addActionListener get-result
                          (proxy [ActionListener] []
                                 (actionPerformed [evt]
                                                  (.setText result (str (operator (Double/parseDouble (.getText value-1))
                                                                                  (Double/parseDouble (.getText value-2))))))))
      (.addActionListener parens-1
                          (proxy [ActionListener] []
                                 (actionPerformed [evt]
                                                  (.setText value-1 (calculate)))))
      
      ; Putting the elements on the grid
      (doto frame
            (.setLayout (GridLayout. 3 3))
            (.add value-1) (.add operator-selector) (.add value-2)
            (.add operate-on-1) (.add result) (.add operate-on-2)
            (.add parens-1) (.add get-result) (.add parens-2)
            (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
            (.setSize 450 120)
            (.setVisible true))))
  
  (defn -main [& cl-args]
    (calculator-base))

