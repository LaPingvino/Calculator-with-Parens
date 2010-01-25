(ns calculator (:gen-class))

(import '(javax.swing JFrame JLabel JTextField JButton UIManager)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout)
        '(java.math.BigDecimal)
        '(java.lang.Math))

(defn button-listener [name frame whattodo]
  (.addActionListener name (proxy [ActionListener] [] (actionPerformed [evt] (whattodo frame)))))

(defn add-button [frame label whattodo]
  (let [name (JButton. label)]
    (button-listener name frame whattodo)
    (.add frame name)))

(defn apply-calc
  
  ;; Operate on itself
  ([operation value frame]
   (.setText value (str (bigdec (try (operation
                                      (bigdec (str "0" (.getText value)))) (catch Exception e (str "Error: " (.getMessage e)))))))
   (.dispose frame))
  
  ;; Operate on two elements and put away
  ([operation value-1 value-2 result frame]
   (.setText result (str (try (operation
                               (bigdec (str "0" (.getText value-1)))
                               (bigdec (str "0" (.getText value-2)))) (catch Exception e (str "Error: " (.getMessage e))))))
   (.dispose frame)))

(defn do-on-both [value-1 value-2 result]
  (let [frame (JFrame. "Choose operator")]
    (doto frame
          (.setLayout (GridLayout. 2 3))
          (add-button "+" (fn [frame] (apply-calc + value-1 value-2 result frame)))
          (add-button "*" (fn [frame] (apply-calc * value-1 value-2 result frame)))
          (add-button "x^y" (fn [frame] (apply-calc (fn [x y] (Math/pow x y)) value-1 value-2 result frame)))
          (add-button "-" (fn [frame] (apply-calc - value-1 value-2 result frame)))
          (add-button "/" (fn [frame] (apply-calc / value-1 value-2 result frame)))
          (add-button "y√x" (fn [frame] (apply-calc (fn [x y] (Math/pow x (/ 1 y))) value-1 value-2 result frame)))
          (.setSize 450 120)
          (.setVisible true))))

(defn do-on-one [value]
  (let [frame (JFrame. "Choose operator")]
    (doto frame
          (.setLayout (GridLayout. 2 3))
          (add-button "x²" (fn [frame] (apply-calc (fn [x] (* x x)) value frame)))
          (add-button "√" (fn [frame] (apply-calc (fn [x] (Math/sqrt x)) value frame)))
          (add-button "π" (fn [frame] (apply-calc (fn [x] (Math/PI)) value frame)))
          (add-button "sin" (fn [frame] (apply-calc (fn [x] (Math/sin x)) value frame)))
          (add-button "cos" (fn [frame] (apply-calc (fn [x] (Math/cos x)) value frame)))
          (add-button "tan" (fn [frame] (apply-calc (fn [x] (Math/tan x)) value frame)))
          (.setSize 450 120)
          (.setVisible true))))

(defn calculator [put-value clean-result]
  (let [frame (JFrame. "Calculator")
    value-1 (JTextField.) value-2 (JTextField.) result (JTextField.)]
    (doto frame
          (.setLayout (GridLayout. 3 3))
          (.add value-1) (.add value-2)
          (add-button "Do on both" (fn [x] (do-on-both value-1 value-2 result)))
          (add-button "Do with above" (fn [x] (do-on-one value-1)))
          (add-button "Do with above" (fn [x] (do-on-one value-2)))
          (.add result)
          (add-button "( ... )" (fn [x] (calculator value-1 result)))
          (add-button "( ... )" (fn [x] (calculator value-2 result)))
          (add-button "Result" (fn [x] (if put-value
                                                    (do (.setText put-value (.getText result))
                                                        (.setText clean-result "")
                                                      (.dispose frame))
                                                  (System/exit 0))))
          (if (= put-value nil) (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE))
          (.setSize 450 120)
          (.setVisible true))))

(defn -main [& cl-args]
  (UIManager/setLookAndFeel (UIManager/getSystemLookAndFeelClassName))
  (calculator nil nil))

