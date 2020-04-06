'''
    Loi Truong
    CS5001
    Homework 7
    December 7th, 2018
'''
import turtle
import random

SQUARE = 50
n = 4
SCOREFILE = 'scores.txt'


class Board:
    def __init__(self, n):
        ''' Function: __init__
            Parameters: n, an int for # of squares
            Returns: nothing
            Does: Initializes a nested list with dimension nxn and four
            starting tiles. -1 represents white tiles, 1 for black tiles  
        '''
        self.board = [[0] * n for i in range(n)]

        # Set up starting tiles
        self.board[int(n/2 - 1)][int(n/2)] = 1
        self.board[int(n/2)][int(n/2 - 1)] = 1
        self.board[int(n/2 - 1)][int(n/2 - 1)] = -1
        self.board[int(n/2)][int(n/2)] = -1
        
    def draw_lines(self, turt, n):
        turt.pendown()
        turt.forward(SQUARE * n)
        turt.penup()

    def draw_board(self, board, n):
        ''' Function: draw_board
            Parameters: board, the nested list representing the board; n, an int
            for # of squares
            Returns: nothing
            Does: Draws an nxn board with a green background and tiles. This is
            constantly updated to reflec the current state of the game 
        '''

        turtle.setup(n * SQUARE + SQUARE, n * SQUARE + SQUARE)
        turtle.screensize(n * SQUARE, n * SQUARE)
        turtle.bgcolor('white')

        # Create the turtle to draw the board
        othello = turtle.Turtle()
        othello.penup()
        othello.speed(0)
        othello.hideturtle()

        # Line color is black, fill color is green
        othello.color("black", "forest green")
        
        # Move the turtle to the upper left corner
        corner = -n * SQUARE / 2
        othello.setposition(corner, corner)
      
        # Draw the green background
        othello.begin_fill()
        for i in range(4):
            othello.pendown()
            othello.forward(SQUARE * n)
            othello.left(90)
        othello.end_fill()

        # Draw the horizontal lines
        for i in range(n + 1):
            othello.setposition(corner, SQUARE * i + corner)
            self.draw_lines(othello, n)

        # Draw the vertical lines
        othello.left(90)
        for i in range(n + 1):
            othello.setposition(SQUARE * i + corner, corner)
            self.draw_lines(othello, n)


    def draw_circle(self, player):
        ''' Function: draw_circle
            Parameters: player, an int for the player, -1 for white and
            1 for black
            Returns: nothing
            Does: Draw a tile whose color depends on the player
        '''
        if player > 0:
            turtle.fillcolor('black')
        else:
            turtle.fillcolor('white')

        turtle.speed(0)
        turtle.hideturtle()
        turtle.down()
        turtle.begin_fill()
        turtle.circle(SQUARE/2)
        turtle.end_fill()
        turtle.up()


    def find_tile_starting_cor(self, board, x, y):
        ''' Function: find_tile_starting_cor
            Parameters: board, a nested list; x and y as the row and column
            of the nested list
            Returns: the coordinates of the middle bottom of a square  
            Does: Finds the coordinates inside each square to start
            drawing tile for that square
        '''
        tile_xcor = (-SQUARE * (n - 1)) / 2 + (SQUARE * y)
        tile_ycor = SQUARE * (n/2 - 1 - x)
        return [tile_xcor, tile_ycor]

    def draw_tiles(self, board):
        ''' Function: draw_tiles
            Parameters: board (a nested list)
            Returns: nothing
            Does: Loops through the nested list and draws the tiles according
            to the elements in the list. -1 for white tiles, 1 for black tiles
        '''
        for x in range(len(self.board)):
            for y in range(len(self.board)):
                if self.board[x][y] > 0:
                    self.draw_tile(board, x, y, 1)
                elif self.board[x][y] < 0:
                    self.draw_tile(board, x, y, -1)
        turtle.up()
        
    def draw_tile(self, board, x, y, player):
        ''' Function: draw_tile
            Parameters: board (a nested list), x and y (int) as row and column;
            player (an int) for the player (-1 for white and 1 for black)
            Returns: nothing
            Does: Calls the find_tile_starting_cor to set the turtle at
            the coordinates of a square and starts drawing the tile for
            that square
        '''
        tile_cor = self.find_tile_starting_cor(board, x, y)
        turtle.setx(tile_cor[0])
        turtle.sety(tile_cor[1])
        self.draw_circle(player)
    
    def move(self, board, move, player):
        ''' Function: move
            Parameters: board (a nested list), move (a tuple that has the row
            and column of the move, as well as its legal directions), player
            (int) representing the player (1 for black, -1 for white)
            Returns: the updated board after the move is made 
            Does: Places and draws the tile at the chosen row and column,
            flips any tile from the other player in between the player's tiles
        '''
        x = move[0]
        y = move[1]

        # Black case:
        if player > 0:

            # Loops through the possible legal direction of the chosen move
            for i in range(len(move[2])):
                while board[x][y] < player:
                    # Places and draws the tile 
                    board[x][y] = player
                    self.draw_tile(board, x, y, player)

                    # Temporarily sets the tile back to none 
                    board[move[0]][move[1]]= 0
                    # Updates the direction while flipping any tiles in between
                    x += move[2][i][0]
                    y += move[2][i][1]
                x = move[0]
                y = move[1]

            # Returns the tile we originally placed 
            board[move[0]][move[1]] = 1
            return board

        # White case
        else:    
            for i in range(len(move[2])):
                while board[x][y] > player:
                    board[x][y] = player
                    self.draw_tile(board, x, y, player)
                    board[move[0]][move[1]] = 0
                    x += move[2][i][0]
                    y += move[2][i][1]
                x = move[0]
                y = move[1]
            board[move[0]][move[1]] = -1
            return board
        
    def simulation_move(self, board, move, player):
        ''' Function: simulation_move
            Parameters: board (a nested list), move (a tuple representing
            the row and column of the move), player (int) representing the
            player (1 for black, -1 for white)
            Returns: the updated board after the move is made 
            Does: Hypothetically places the tile according to the chosen move,
            flips any tile from the other player in between the player's tiles.
            This is used for simulation to choose the best move for the
            computer 
        '''
        x = move[0]
        y = move[1]
        for i in range(len(move[2])):
            while board[x][y] > player:
                board[x][y] = player
                board[move[0]][move[1]] = 0
                x += move[2][i][0]
                y += move[2][i][1]
            x = move[0]
            y = move[1]
        board[move[0]][move[1]] = -1
        return board
        
    def is_legal_direction(self, row, column, board, player, j, k):
        ''' Function: is_legal_direction
            Parameters: row and column (an int), board (a nested list),
            player (an int representing each player), j and k (an int from:
            -1 to 0 to 1)
            Returns: boolean
            Does: Evaluates 8 possible directions of a move and returns True
            only if there is a tile from the opposite player next to that and
            at the end of the direction there is a tile from the player to
            allow flipping 
        '''
        
        legal_dir = False
        legal_dir_for_flipping = False

        # An occupied square is not a legal move  
        if board[row][column] != 0:
            return legal_dir

        # Check all 8 directions of a hypothetical move 
        row += j
        column += k
        count = 0

        # The loop runs when the new direction is not out of the board 
        while (0 <= row and row < len(board)) and \
              (0 <= column and column < len(board)):
            count += 1

            # Black case 
            if player == 1:

                # This means the tile in the direction is from the opposite
                # player 
                if board[row][column] < 0:
                    legal_dir = True

                # This means there is no tile in the direction
                elif board[row][column] == 0:
                    legal_dir = False
                    break

                # This means there is a tile from the player at the end
                # of the direction that would allow flipping 
                else:
                    legal_dir_for_flipping = True
                    break

            # White case 
            else:
                if board[row][column] > 0: 
                    legal_dir = True
                elif board[row][column] == 0 :
                    legal_dir = False
                    break
                else:
                    legal_dir_for_flipping = True
                    break
            row += j
            column += k

        
        if count < 2:
            return False
        
        return legal_dir and legal_dir_for_flipping
        
    def legal_move(self, row, column, board, player):
        ''' Function: legal_move
            Parameters: row and column (an int), board (nested list), player
            (an int representing each player)
            Returns: a list of legal directions of a hypothetical move
            Does: Evaluates all 8 possible directions of a hypothetical move by
            calling the is_legal_direction function 
        '''
        legal_move_list = []

        # j and k are the factors for eight directions 
        for k in (-1, 0, 1):
            for j in (-1, 0, 1):
                if (k != 0 or j != 0) and \
                   self.is_legal_direction(row, column, board, player, j, k):
                    legal_move_list.append((j,k))
        return legal_move_list
            
               
    def find_moves(self, board, player):
        ''' Function: moves
            Parameters: board (a nested list), player (an int representing each
            player)
            Returns: a list of possible moves. Each element of the list is a
            tuple of row, column of a move, and the list of legal directions
            associated with that move 
            Does: Loops through the board, calls the legal_move function to
            determine the possible moves 
        '''
        moves_list = []
        for column in range(len(self.board)):
            for row in range(len(self.board)):
                if self.legal_move(row, column, board, player) != []:
                    moves_list.append((row, column, self.legal_move(row,
                                                                    column,
                                                                    board,
                                                                    player)))
        return moves_list

    def get_new_board(self):
        ''' Function: get_new_board 
            Parameters: self 
            Returns: a completely new and empty nested list  
            Does: Creates a nested list to represent the data structure of a new
            board. This nested list only has elements 0, indicating the board
            is empty and has no tiles 
        '''
        new_board = [[0] * n for i in range(n)]
        return new_board

    def make_board_copy(self, board):
        ''' Function: make_board_copy 
            Parameters: self, board (a nested list) 
            Returns: a nested list  
            Does: Creates a copy of the current board structure to be used
            for simulation 
        '''
        board_copy = self.get_new_board()

        for x in range(n):
            for y in range(n):
                board_copy[x][y] = board[x][y]
        return board_copy 

    def is_on_corner(self, row, column):
        ''' Function: is_on_corner  
            Parameters: row and column (int)  
            Returns: boolean 
            Does: Checks whether a move is at the corner to the board 
        '''
        return (row == 0 and column == 0) or \
               (row == (n - 1) and column == 0) or \
               (row == 0 and column == (n - 1)) or \
               (row == (n - 1) and column == (n - 1))

    def select_move(self, moves, player):
        ''' Function: select_move
            Parameters: moves (a list of possible moves), player (an int,
            -1 for white, 1 for black)
            Returns: a move from the list of possible legal moves
            Does: Loops through the possible legal moves of the player and
            picks the best move. Corner moves are the best moves. If
            no corner moves exist, creates a simulation for each possible
            legal move and picks the one that can flip the most tiles 
        '''
        # Always picks corner moves if they exist 
        for i in range(len(moves)):
            if self.is_on_corner(moves[i][0], moves[i][1]):
                return moves[i]

        # Otherwise, simulates each possible move, compare the scores
        # after the simulation to see what move can flip the most tiles 
        best_score = -1
        for i in range(len(moves)):
            board_copy = self.make_board_copy(self.board)
            self.simulation_move(board_copy, moves[i], -1)
            score = self.score_board(board_copy)[1]
            if score > best_score:
                best_move = moves[i]
                best_score = score
        return best_move 
            
    def score_board(self, board):
        ''' Function: score_board
            Parameters: board (a nested list)
            Returns: a list of scores of player's (number of black tiles) and
            computer's (number of white tiles) 
            Does: Loops through the board and counts the number of black
            and white tiles 
        '''
        black = 0
        white = 0
        for i in range(len(board)):
            for j in range(len(board[i])):
                if board[i][j] == 1:
                    black += 1
                elif board[i][j] == -1:
                    white += 1
        return [black, white]

    def write_score(self, user_name, score, file_name = SCOREFILE):
        ''' Function: write_score
            Input: username (string), score (an integer)
            Returns: nothing
            Does: Creates a file named scores.txt and writes the user name
            and score to the file. This applies to first user only 
        '''
        try:
            outfile = open(file_name, 'w')
            outfile.write(user_name + ' ' + str(score) + '\n')
            outfile.close()
        except OSError:
            print('Unable to store your name and score')
            return
        
    def write_arranged_scores(self, user_name, score, file_name = SCOREFILE):
        ''' Function: write_arranged_scores
            Input: username (string), score (integer), file scores.txt
            (optional)
            Returns: nothing
            Does: Executes function write_score if this is a first user.
            Otherwise, opens existing scores.txt file, writes username and
            score to the top of the file if highest score. If not highest score,
            writes username and score to bottom of file 
        '''
        try:
            infile = open(file_name, 'r')
            all_score = infile.read()
            lines = all_score.splitlines()
            infile.close()
            all_data = []
            for line in lines:
                all_data.append(line.split(' '))
            if score > int(all_data[0][1]):
                final_data = [user_name + ' ' + str(score)] + lines
            else:
                final_data = lines + [user_name + ' ' + str(score)]

            outfile = open(file_name, 'w')
            for data in final_data:
                outfile.write(data + '\n')
            outfile.close()
            
        except OSError:
            self.write_score(user_name, score)
            return
        
    def find_row(self, y):
        ''' Function: find_row  
            Input: y (float) - the y coordinate of the turtle click 
            Returns: the row of the board where the user clicks  
            Does: Calculates the row of the board where the user clicks
        '''        
        start = -(n * SQUARE) / 2
        row = int((-start - y) // SQUARE)
        return row

    def find_column(self, x):
        ''' Function: find_column  
            Input: x (float) - the x coordinate of the turtle click 
            Returns: the column of the board where the user clicks  
            Does: Calculates the column of the board where the user clicks
        ''' 
        start = -(n * SQUARE) / 2
        column = int((-start + x) // SQUARE)
        return column 

    def play_human(self, x, y):
        ''' Function: play_human 
            Input: x, y (float) - the coordinates of the turtle click 
            Returns: nothing
            Does: Responds to the player's click and places the tile.
            Switches to the computer if there's no moves left 
        '''
        # Find all the possible moves 
        black_moves = self.find_moves(self.board, 1)

        # If there's a move, then evaluates the click
        
        if len(black_moves) > 0:
        
            row = self.find_row(y)
            column = self.find_column(x)
            # Prompts user to click inside the board 
            if (row < 0 or row >= n) or (column < 0 or column >= n):
                print('Please click inside the board')

            # If they click on a valid square, then places the tile and flips
            else:
                for i in range(len(black_moves)):
                    if black_moves[i][0] == row \
                       and black_moves[i][1] == column:
                        self.move(self.board, black_moves[i], 1)
                        turtle.onscreenclick(None)
                        self.play_computer()

        # If there's no possible move left then it's the computer's turn
        else:
            print("You don't have any available moves left\n")
            turtle.onscreenclick(None)
            self.play_computer()
        
    def play_computer(self):
        ''' Function: play_computer 
            Input: self  
            Returns: nothing
            Does: Plays as the computer. Chooses the best possible moves.
            If there's no moves then pass to the player. If both players have
            no possible moves then ends the game. 
        '''
        # Ends game if no player has available moves 
        white_moves = self.find_moves(self.board, -1)
        black_moves = self.find_moves(self.board, 1)
        if not black_moves and not white_moves:
            self.end_game()
            

        print("It's the computer's turn\n")

        # Computer plays if it has available moves. Then passes the turn 
        if len(white_moves) > 0:
            move = self.select_move(white_moves,-1)
            self.move(self.board, move, -1)
            black_moves = self.find_moves(self.board, -1)
            white_moves = self.find_moves(self.board, 1)
            if not black_moves and not white_moves:
                self.end_game()
            else:
                print("It's your turn. Please click on the board\n")
                turtle.onscreenclick(self.play_human)

        # If computer doesn't have available moves then turn passes to player
        else:
            print("Computer has no moves left\n")
            white_moves = self.find_moves(self.board, -1)
            black_moves = self.find_moves(self.board, 1)
            if not black_moves and not white_moves:
                self.end_game()
            else: 
                print("It's your turn. Please click on the board\n")
                turtle.onscreenclick(self.play_human)
                

    def start_game(self):
        ''' Function: start_game  
            Input: self  
            Returns: nothing
            Does: Draws the board, starts the game, and draws the tiles
            according to the current state of the board 
        '''
        self.draw_board(self.board, n)
        self.draw_tiles(self.board)
        turtle.onscreenclick(self.play_human)

    def end_game(self):
        ''' Function: end_game  
            Input: self  
            Returns: nothing
            Does: Ends the game, announces the winner/loser and scores.
            Write the score into a text file 
        '''
        print("\nGame over")
        print("Your score: ", self.score_board(self.board)[0])
        print("Computer score: ", self.score_board(self.board)[1])
        if self.score_board(self.board)[0] > self.score_board(self.board)[1]:
            print("Victory is yours!!!")
        elif self.score_board(self.board)[0] == self.score_board(self.board)[1]:
            print("It was a tie!")
        else:
            print("Good luck next time!!!")

        # Prompts user for name and writes score 
        user_name = input('\nPlease enter your name for the Hall of Fame\n')
        self.write_arranged_scores(user_name, self.score_board(self.board)[0])
        turtle.bye()
        exit()
    
def main():
    othello = Board(n)
    othello.start_game()
    print("You are Black player. Please go first\n")

main()

