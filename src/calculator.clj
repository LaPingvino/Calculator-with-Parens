(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(java.math.BigDecimal))

(defn get-operator [operator-field]
  (let [frame (JFrame. "Select operator to be used")
    ; Elements ordered by appearance on the calculator
    op-+ (JButton. "+") op-- (JButton. "-")
    op-* (JButton. "*") op-/ (JButton. "/")]
    
    ; Action listeners to Get Things Done
    (.addActionListener op-+
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (.setText operator-field "+"))))
    (.addActionListener op--
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (.setText operator-field "-"))))
    (.addActionListener op-*
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (.setText operator-field "*"))))
    (.addActionListener op-/
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (.setText operator-field "/"))))
    
    ; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 2 2))
          (.add value-1) (.add operator-selector) (.add value-2)
          (.add operate-on-1) (.add result) (.add operate-on-2)
          (.add parens-1) (.add get-result) (.add parens-2)
          (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
          (.setSize 80 80)
          (.setVisible true))))

(defn calculator [put-value clean-result]
  (let [frame (JFrame. "Calculator")
    ; Elements ordered by appearance on the calculator
    value-1 (JTextField.) operator-selector (JButton. "?") value-2 (JTextField.)
    parens-1 (JButton. "( ... )") result (JTextField.) parens-2 (JButton. "( ... )")
    operate-on-1 (JButton. "Put result") get-result (JButton. "=") operate-on-2 (JButton. "Put result")]
    
    ; Action listeners to Get Things Done
    (.addActionListener get-result
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (if (= (.getText result) "")
                                                    (.setText result (str ((eval (.getText result))
                                                                           (bigdec (str "0"(.getText value-1)))
                                                                           (bigdec (str "0" (.getText value-2))))))
                                                  (if put-value
                                                      (do (.setText put-value (.getText result))
                                                          (.setText clean-result "")
                                                        (.dispose frame))
                                                    (System/exit 0))))))
    (.addActionListener parens-1
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-1 result))))
    (.addActionListener parens-2
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-2 result))))
    (.addActionListener operator-selector
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (get-operator operator-selector))))
    
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
    (calculator nil nil))

