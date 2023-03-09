import csv
import json

input_file_name = "marker_test.csv"
output_file_name = "data.txt"
with open(input_file_name, "r") as input_file, \
        open(output_file_name, "w") as output_file:
        
    reader = csv.reader(input_file)
    # 첫 줄은 col_names 리스트로 읽어 놓고

    col_names = next(reader)
    # 그 다음 줄부터 zip으로 묶어서 json으로 dumps

    data = []
    for cols in reader:
        doc = {col_name: col for col_name, col in zip(col_names, cols)}
        data.append(doc)
    print(json.dumps(data, ensure_ascii=False), file=output_file)