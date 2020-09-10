(ns tictac.core
  (:gen-class))

;https://github.com/tbaik/clojure-ttt
;the tictactoe version I read before making this.
;I read it, liked it. Tried to forget it.
;then wrote this.
;I'm guessing they are very similer.
;if you want to go and read the original.

;should I avoid using declare?

(declare getInput)

(declare exitGame?)

(def newState [[0 1 2] [3 4 5] [6 7 8]])

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
  
;this is ugly, make better
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

(defn movesAvailable
 [board]
 (count (mapcat valid-moves board)))

(defn updateBoard
  [num mark]
  (let [cellCordinates (getCell num)
        y (get-in cellCordinates [0])
        x (get-in cellCordinates [1])]
    (if (number? (get-in @state [y x]))
       (swap! state assoc-in [y x] mark)
       (getInput "choose empty cell"))))

;reason for let is so that it fixes
; situations like -> X wins but prints O has won
(defn game
  [num]
  (let [currentMark (nextMark @state)]
    (updateBoard num currentMark)
    (cond
      (hasWinner? @state)
      (do (println (str "player " currentMark "has won"))
          (exitGame? "e"))
      (= (movesAvailable @state) 0)
      (do (println "nobody won. run program to play again")
          (exitGame? "e"))))
 (getInput))

(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)))

(defn exitGame?
  [lowerCaseE]
  (and (= "e" lowerCaseE) (System/exit 0)))

(defn printBoard
  [board]
  (doseq [a board]
   (println a)))

(defn getInput
  ([] (println "choose cell ")
   (printBoard @state)
   (flush)
   (let [userInput (read-line)]
     (exitGame? userInput)
     (if (acceptableAnwser? userInput)
       (game (Integer/parseInt userInput))
       (getInput "please type cell number, e - exit game"))))
  ([message] (println message)
   (getInput)))

(defn -main
  [& args]
  (getInput))


;wtf does this do?
; (defmacro declare
;   "defs the supplied var names with no bindings, useful for making forward declarations."
;   {:added "1.0"}
;   [& names] `(do ~@(map #(list 'def (vary-meta % assoc :declared true)) names)))
