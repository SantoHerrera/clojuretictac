(ns tictac.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn square-matrix
  [n p]
  (->> p (repeat n) vec (repeat n) vec))


(defn updateNestedVec [nestedVec x y changeTo]
  (assoc-in nestedVec [x y] changeTo))


(defn state
  []
  (def currentBoard (atom (square-matrix 3 "E"))))


(defn getAllE
  [vec]
  (filter (fn [x] (= "E" x) ) vec))


(defn vectorlengthEven?
  [vec]
  (even? (count vec)))


(defn xNext?
  "takes 2d vector ex. [[1 2 3] ['E' 5 'E'] ['E' 'E' 9]]"
  [board]
  (let [allE (mapcat getAllE board)]
   (vectorlengthEven? allE)))
  

(defn getCell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (println y x)))







;why does this work
(defn ik [n p]
  (->> p (repeat n) vec))
;and not this
;(defn idk [n p]
;  (p (repeat n) vec))
(idk 3 3)
