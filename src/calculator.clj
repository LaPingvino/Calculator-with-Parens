(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(java.math.BigDecimal))

(defn do-operation [value-1 value-2 result]
  (let [frame (JFrame. "Calculator")
    ; Elements ordered by appearance on the calculator
    op-+ (JButton. "+") op-* (JButton. "*")
    op-- (JButton. "-") op-div (JButton. "/")]
    
    ; Action listeners to Get Things Done
    (.addActionListener op-+
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do
                                                    (.setText result (str (+
                                                                           (bigdec (str "0" (.getText value-1)))
                                                                           (bigdec (str "0" (.getText value-2))))))
                                                    (.dispose frame)))))
    (.addActionListener op--
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do
                                                    (.setText result (str (-
                                                                           (bigdec (str "0" (.getText value-1)))
                                                                           (bigdec (str "0" (.getText value-2))))))
                                                    (.dispose frame)))))
    (.addActionListener op-*
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do
                                                    (.setText result (str (*
                                                                           (bigdec (str "0" (.getText value-1)))
                                                                           (bigdec (str "0" (.getText value-2))))))
                                                    (.dispose frame)))))
    
    (.addActionListener op-div
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do
                                                    (.setText result (str (/
                                                                           (bigdec (str "0" (.getText value-1)))
                                                                           (bigdec (str "0" (.getText value-2))))))
                                                    (.dispose frame)))))
    
    ; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 2 2))
          (.add op-+) (.add op--)
          (.add op-*) (.add op-div)
          (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
          (.setSize 450 120)
          (.setVisible true))))

(defn calculator [put-value clean-result]
  (let [frame (JFrame. "Calculator")
    ; Elements ordered by appearance on the calculator
    value-1 (JTextField.) operator-sel (JButton. "Do on both")  value-2 (JTextField.)
    parens-1 (JButton. "( ... )") result (JTextField.) parens-2 (JButton. "( ... )")
    operate-on-1 (JButton. "Do with above") get-result (JButton. "Return") operate-on-2 (JButton. "Do with above")]
    
    (.addActionListener operator-sel
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do-operation value-1 value-2 result))))
    (.addActionListener parens-1
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-1 result))))
    (.addActionListener parens-2
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-2 result))))
    (.addActionListener get-result
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (if put-value
                                                    (do (.setText put-value (.getText result))
                                                        (.setText clean-result "")
                                                      (.dispose frame))
                                                  (System/exit 0)))))
    
    ; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 3 3))
          (.add value-1) (.add value-2) (.add operator-sel)
          (.add operate-on-1) (.add operate-on-2) (.add result)
          (.add parens-1) (.add parens-2) (.add get-result)
          (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
          (.setSize 450 120)
          (.setVisible true))))

(defn -main [& cl-args]
  (calculator nil nil))

