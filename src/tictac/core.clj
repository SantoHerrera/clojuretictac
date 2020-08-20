(ns tictac.core
  (:gen-class))

;https://github.com/tbaik/clojure-ttt
;the ttt version I read before making this.
;I read it, liked it. Tried to forget it.
;then wrote this.
;I guessing they are very similer.
;if you want to go and read the original.




(defn square-matrix
  [n p]
  (->> p (repeat n) vec (repeat n) vec))

(def newState [[0 1 2] [3 4 5] [6 7 8]])


; (def state (atom (square-matrix 3 "E")))
(def state (atom newState))



(defn getNumbers
  [vec]
  (filter integer? vec))


(defn nextMark
  "takes 2d vector ex. [[1 2 3] ['x' 5 'o'] ['x' 'o' 9]]"
  [board]
  (let [allNums (mapcat getNumbers board)]
      (if (even? (count allNums))
        (str "O")
        (str "X"))))



(defn getCell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (vector y x)))


(defn contains-same-pieces
  [coll]
  (apply = coll))




(defn diagonalWinner?
  [board]
  (let [[r1 r2 r3] board
        [zero one two] r1
        [three four] r2
        [six seven eight] r3]
    (or (contains-same-pieces (vector zero four eight))
        (contains-same-pieces (vector two four six)))))





(defn hasHorizontalWinner?
  [board]
  (some true? (map contains-same-pieces board)))

(defn hasVerticalWinner?
  [board]
  (some true? (map contains-same-pieces (apply map vector board))))


(defn valid-moves
  [board]
  (filter integer? board))



(defn hasWinner?
  [board]
  (and (< (count (mapcat valid-moves board)) 5)
       (or (hasHorizontalWinner? board)
           (hasVerticalWinner? board)
           (diagonalWinner? board))))


(defn updateBoard
  [num mark]
  (let [cellCordinates (getCell num)
        y (get-in cellCordinates [0])
        x (get-in cellCordinates [1])]
    (if (number? (get-in @state [y x]))
       (swap! state assoc-in [y x] mark)
       (println state "fuck"))))


(defn game
  [num]
  (updateBoard num (nextMark @state))
  (println "at the end this gets called too" state (hasWinner? @state)))


(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)))

(defn getInput
  [word]
  (if (acceptableAnwser? word)
      (game (Integer/parseInt word))
      (println "it didnt work")))


(getInput "5")

;2 and 6 aswell


; (defn hasWinner?
;   [board]
;   (and (< (count (mapcat valid-moves board)) 5)
;        (or (hasHorizontalWinner? board)
;            (hasVerticalWinner? boar))))

;todo
;move the let block inside game function into updateBoard funciton



;what to do
;make state into [[0 1 2] [3 4 5] [6 7 8]]
; instead of being filled with E's




















;accept user input
;if its not a number ask for a number
;if its the number 9 ask for number 0 - 8
;  else println yayyyyy



;why does this work
; (defn ik [n p]
;   (->> p (repeat n) vec))


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
