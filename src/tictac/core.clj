(ns tictac.core
  (:gen-class))

(defn square-matrix
  [n p]
  (->> p (repeat n) vec (repeat n) vec))


(def state (atom (square-matrix 3 "E")))


(defn vectorlengthEven?
  [vec]
  (even? (count vec)))


(defn getAllE
  [vec]
  (filter (fn [x] (= "E" x) ) vec))



(defn nextMark
  "takes 2d vector ex. [[1 2 3] ['E' 5 'E'] ['E' 'E' 9]]"
  [board]
  (let [allE (mapcat getAllE board)]
    (if (vectorlengthEven? allE)
      (str "X")
      (str "O"))))


(defn getCell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (vector y x)))


(defn updateBoard
  [y x changeTo]
  (swap! state assoc-in [y x] changeTo))


(defn game
  [num]
  (let [cellCordinates (getCell num)
        y (get-in cellCordinates [0])
        x (get-in cellCordinates [1])
        mark (nextMark @state)]
    (updateBoard y x mark)))


(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)));


(defn getInput
  [word]
  (if (acceptableAnwser? word)
      (game (Integer/parseInt word))
      (println "it didnt work")))



















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


;; the one that runs from command prompt
;; (defn getInput
;;   []
;;   (println "Enter word to find definition ->")
;;   (flush)
;;   (let [numberEntered (read-line)]
;;     (if (acceptableAnwser? numberEntered)
;;       (println "you worked bitch" numberEntered)
;;       (println "it didnt work"))))
