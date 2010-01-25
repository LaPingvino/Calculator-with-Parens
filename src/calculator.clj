(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton UIManager)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(java.math.BigDecimal)
	'(java.lang.Math))


(defn apply-calc

;; Operate on itself
  ([operation value frame]
   (.setText value (str (bigdec (try (operation
                         (bigdec (str "0" (.getText value)))) (catch Exception e "#!ERR")))))
   (.dispose frame))
  
;; Operate on two elements and put away
  ([operation value-1 value-2 result frame]
   (.setText result (str (try (operation
                          (bigdec (str "0" (.getText value-1)))
                          (bigdec (str "0" (.getText value-2)))) (catch Exception e "#!ERR"))))
   (.dispose frame)))

(defn do-on-both [value-1 value-2 result]
  (let [frame (JFrame. "Calculator")
    ;; Elements ordered by appearance on the calculator
    op-+ (JButton. "+") op-* (JButton. "*") op-pow (JButton. "x^y")
    op-- (JButton. "-") op-div (JButton. "/") op-root (JButton. "y√x")]
    
    ;; Action listeners to Get Things Done
    (.addActionListener op-+
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc + value-1 value-2 result frame))))
    (.addActionListener op--
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc - value-1 value-2 result frame))))
    (.addActionListener op-*
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc * value-1 value-2 result frame))))
    (.addActionListener op-div
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc / value-1 value-2 result frame))))
    (.addActionListener op-pow
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc (fn [x y] (Math/pow x y)) value-1 value-2 result frame))))
    (.addActionListener op-root
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc (fn [x y] (Math/pow x (/ 1 y))) value-1 value-2 result frame))))
    
    ;; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 2 3))
          (.add op-+) (.add op--) (.add op-pow)
          (.add op-*) (.add op-div) (.add op-root)
          (.setSize 450 120)
          (.setVisible true))))

(defn do-on-one [value]
  (let [frame (JFrame. "Calculator")
    ;; Elements ordered by appearance on the calculator
    op-square (JButton. "x²") op-sqrt (JButton. "√")]
    
    ;; Action listeners to Get Things Done
    (.addActionListener op-square
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc (fn [x] (* x x)) value frame))))
    (.addActionListener op-sqrt
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (apply-calc (fn [x] (Math/sqrt x)) value frame))))
    
    ;; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 1 2))
          (.add op-square) (.add op-sqrt)
          (.setSize 450 120)
          (.setVisible true))))

(defn calculator [put-value clean-result]
  (let [frame (JFrame. "Calculator")
    ;; Elements ordered by appearance on the calculator
    value-1 (JTextField.) operator-sel (JButton. "Do on both")  value-2 (JTextField.)
    parens-1 (JButton. "( ... )") result (JTextField.) parens-2 (JButton. "( ... )")
    operate-on-1 (JButton. "Do with above") get-result (JButton. "Return") operate-on-2 (JButton. "Do with above")]
    
    (.addActionListener operator-sel
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do-on-both value-1 value-2 result))))
    (.addActionListener parens-1
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-1 result))))
    (.addActionListener parens-2
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (calculator value-2 result))))
    (.addActionListener operate-on-1
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do-on-one value-1))))
    (.addActionListener operate-on-2
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (do-on-one value-2))))
    (.addActionListener get-result
                        (proxy [ActionListener] []
                               (actionPerformed [evt]
                                                (if put-value
                                                    (do (.setText put-value (.getText result))
                                                        (.setText clean-result "")
                                                      (.dispose frame))
                                                  (System/exit 0)))))
    
    ;; Putting the elements on the grid
    (doto frame
          (.setLayout (GridLayout. 3 3))
          (.add value-1) (.add value-2) (.add operator-sel)
          (.add operate-on-1) (.add operate-on-2) (.add result)
          (.add parens-1) (.add parens-2) (.add get-result)
          (if (= put-value nil) (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE))
          (.setSize 450 120)
          (.setVisible true))))

(defn -main [& cl-args]
  (UIManager/setLookAndFeel (UIManager/getSystemLookAndFeelClassName))
  (calculator nil nil))

