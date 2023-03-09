# 시군동 csv 파일 파싱
import pprint
import pandas as pd
mr = pd.read_csv("C:/Users/82108/Desktop/adm_code.csv", header=None, encoding='cp949')

si = {}

for row_index, row in mr.iterrows():
    # print(row_index)
    # print(row)
    if row_index == 0:
        continue

    if row.loc[1] not in si:
        si[row.loc[1]] = {}
    if row.loc[3] not in si[row.loc[1]]:
        si[row.loc[1]][row.loc[3]] = []
    if row.loc[5] not in si[row.loc[1]][row.loc[3]]:
        si[row.loc[1]][row.loc[3]].append(row.loc[5])

# pp = pprint.PrettyPrinter()
# pp.pprint(si)
print(si)