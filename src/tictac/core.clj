(ns tictac.core
  (:gen-class))


(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)));



(defn square-matrix
  [n p]
  (->> p (repeat n) vec (repeat n) vec))

(def newState (square-matrix 3 "E"))

(def state (atom (square-matrix 3 "E")))

(assoc-in state [0 0] "fuck")

(swap! state assoc-in [2 0] "dam") ;this works just remember to reset atom dumbass

(get-in state [0])

(swap! state assoc 0 "fuck")


(defn getInput
  []
  (println "Enter word to find definition ->")
  (flush)
  (let [numberEntered (read-line)]
    (if (acceptableAnwser? numberEntered)
      ( "you worked bitch" numberEntered)
      (println "it didnt work"))))


(defn getCell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (vector y x)))

(defn updateNestedVec [nestedVec x y changeTo]
  (assoc-in nestedVec [x y] changeTo))

(def state2 (square-matrix 3 3))
(println state)


(swap! state update-in [0 0] "x")

(updateNestedVec state2 0 0 "x")



(defn setUp
  [num]
  (let ;[number (Integer/parseInt num)
   [cellCordinates (getCell num)] cellCordinates))


(defn updateBoard 
  [y x changeTo]
  (swap! state update-in [y x] (str changeTo)))



(defn getInputV2
  [word]
  (let [numberEntered word]
    (if (acceptableAnwser? numberEntered)
      (updateBoard (Integer/parseInt word) 0 "shirworkednigga")
      (println "it didnt work"))))

(getInputV2 "2")


(defn updateNestedVec 
  [nestedVec x y changeTo]
  (assoc-in nestedVec [x y] changeTo))


(defn getAllE
  [vec]
  (filter (fn [x] (= "E" x) ) vec))


(defn vectorlengthEven?
  [vec]
  (even? (count vec)))

(vectorlengthEven? [" 4" " 56"])


(defn xNext?
  "takes 2d vector ex. [[1 2 3] ['E' 5 'E'] ['E' 'E' 9]]"
  [board]
  (let [allE (mapcat getAllE board)]
   (vectorlengthEven? allE)))

(defn nextMark
  "takes 2d vector ex. [[1 2 3] ['E' 5 'E'] ['E' 'E' 9]]"
  [board]
  (let [allE (mapcat getAllE board)]
    (if (vectorlengthEven? allE)
      (println "X")
      (println "O"))))



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
