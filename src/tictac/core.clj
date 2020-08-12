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









(defn contains-same-pieces
  [coll]
  (apply = coll))



(defn has-winner-from-separation?
  [separated-coll]
  (some true? (map contains-same-pieces separated-coll)))

(defn separate-into-rows
  [width board]
  (partition width board))


(defn has-horizontal-winner?
  [width board]
  (has-winner-from-separation? (separate-into-rows width board)))

(defn separate-into-columns
  [width board]
  (apply map vector (separate-into-rows width board)))


(defn has-vertical-winner? [width board]
    (has-winner-from-separation? (separate-into-columns width board)))




(defn board-width
   [board]
   (int (Math/sqrt (count board))))

(defn valid-moves
  [board]
  (filter integer? board))


(defn has-winner?
   [board]
;board -> [1 2 3 4 5 x o 8 9]
   (let [width (board-width board)]
        (and (< (count (valid-moves board)) 5) ;im guessing its becuase no one could win with less than 5 pieces on board
             (or (has-horizontal-winner? width board);lit
                 (has-vertical-winner? width board)))));nice


(def board ["x" "x" "x" 3 4 5 6 "o" "o"])

(< (count (valid-moves board)) 5)


(has-horizontal-winner? (board-width board) board)

(has-winner? ["x" "x" "x"])























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
