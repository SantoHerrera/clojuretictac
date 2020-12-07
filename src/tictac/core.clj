(ns tictac.core
  (:gen-class))

;https://github.com/tbaik/clojure-ttt
;the tictactoe version I read before making this.
;I read it, liked it. Tried to forget it.
;then wrote this.
;I'm guessing they are very similer.
;if you want to go and read the original.

;should I avoid using declare?

(def newState [[0 1 2] [3 4 5] [6 7 8]])

(defn get-numbers
  [vec]
  (filter integer? vec))

(defn next-mark
  "takes 2d vector ex. [[1 2 3] ['x' 5 'o'] ['x' 'o' 9]]"
  [board]
  (let [allNums (mapcat get-numbers board)]
      (if (even? (count allNums))
        (str "O")
        (str "X"))))

(defn get-cell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (vector y x)))

(defn contains-same-pieces
  [coll]
  (apply = coll))

;this is ugly, make better
(defn diagonal-winner?
  [board]
  (let [[r1 r2 r3] board
        [zero one two] r1
        [three four] r2
        [six seven eight] r3]
    (or (= (vector zero four eight))
        (= (vector two four six)))))


(defn has-horizontal-winner?
  [board]
  (some true? (map contains-same-pieces board)))

(defn has-vertical-winner?
  [board]
  (some true? (map contains-same-pieces (apply map vector board))))

(defn valid-moves
  [board]
  (filter integer? board))

(defn has-winner?
  [board]
  (and (< (count (mapcat valid-moves board)) 5)
       (or (has-horizontal-winner? board)
           (has-vertical-winner? board)
           (diagonal-winner? board))))

(defn moves-available
 [board]
 (count (mapcat valid-moves board)))


(defn acceptable-answer?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (not= false (string? answer))
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)))


(defn return-acceptable-answer
  [answer]
  (if (and
       (= 1 (count answer))
       (every? #(Character/isDigit %) answer)
       (not= "9" answer))
     (Integer/parseInt answer)
     9))


(defn print-board
  [board]
  (doseq [a board]
   (println a)))


(defn update-board
  [num mark board]
  (if (number? (get-in board (get-cell num)))
    (assoc-in board (get-cell num) mark)
    board))

(defn exit-game?
  [lower-case-e]
  (= "e" lower-case-e))


(defn exit-game
  []
  (System/exit 0))

(defn get-input
  []
  (read-line))


(defn new-game
  []
  (loop [input "9"
         game newState
         moves-availabless (moves-available game)]
        (print-board game)
        ;(println moves-availabless)
        (cond
          (exit-game? input) (exit-game)
          (has-winner? game) (println "fucking won"))
        (recur (read-line)
               (update-board (return-acceptable-answer input) (next-mark game) game)
               (moves-available game))))


(defn -main
  []
  (new-game))














; ;need to if user enters e call exit game
;
; reason for let is so that it fixes
; situations like -> X wins but prints O has won
