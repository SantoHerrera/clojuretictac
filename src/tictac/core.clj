(ns tictac.core
  (:gen-class))


(defn acceptableAnwser?
  "returns true if its a string thats inbetween 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)));



(defn getInput
  []
  (println "Enter word to find definition ->")
  (flush)
  (let [wordEntered (read-line)]
    ;(if (acceptableAnwser? wordEntered)
      (println "you thought bitch" wordEntered)))






(defn -main
  [& arfs]
  (getInput))


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




;accept user input
;if its not a number ask for a number
;if its the number 9 ask for number 0 - 8
;  else println yayyyyy



;why does this work
(defn ik [n p]
  (->> p (repeat n) vec))
;and not this
;(defn idk [n p]
;  (p (repeat n) vec))
;(idk 3 3)
