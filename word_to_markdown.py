import os
import sys
from docx import Document

# 确保安装了python-docx库
try:
    from docx import Document
except ImportError:
    print("Error: python-docx library not installed!")
    print("Please install it first: pip install python-docx")
    sys.exit(1)

# 获取命令行参数
if len(sys.argv) != 3:
    print("Usage: python word_to_markdown.py <input.docx> <output.md>")
    sys.exit(1)

input_path = sys.argv[1]
output_path = sys.argv[2]

# 检查输入文件是否存在
if not os.path.exists(input_path):
    print(f"Error: Input file '{input_path}' not found!")
    sys.exit(1)

# 读取Word文档
try:
    doc = Document(input_path)
    print(f"Reading Word document: {input_path}")
    
    # 提取文本内容
    markdown_content = ""
    
    for para in doc.paragraphs:
        if not para.text.strip():
            continue
            
        # 检查段落样式，确定标题级别
        style_name = para.style.name
        text = para.text.strip()
        
        if "Heading 1" in style_name or "标题 1" in style_name:
            # 一级标题
            markdown_content += f"# {text}\n\n"
        elif "Heading 2" in style_name or "标题 2" in style_name:
            # 二级标题
            markdown_content += f"## {text}\n\n"
        elif "Heading 3" in style_name or "标题 3" in style_name:
            # 三级标题
            markdown_content += f"### {text}\n\n"
        elif "Heading 4" in style_name or "标题 4" in style_name:
            # 四级标题
            markdown_content += f"#### {text}\n\n"
        elif "List" in style_name or "列表" in style_name:
            # 列表项
            markdown_content += f"- {text}\n"
        else:
            # 普通段落
            markdown_content += f"{text}\n\n"
    
    # 处理表格
    for table in doc.tables:
        markdown_content += "\n\n"
        for i, row in enumerate(table.rows):
            row_cells = [cell.text.strip() for cell in row.cells]
            markdown_content += "| " + " | ".join(row_cells) + " |\n"
            # 添加表头分隔线
            if i == 0:
                header_cells = ["---" for _ in row_cells]
                markdown_content += "| " + " | ".join(header_cells) + " |\n"
        markdown_content += "\n\n"
    
    # 清理多余的空行和格式化问题
    markdown_content = markdown_content.replace("\n\n\n", "\n\n")
    markdown_content = markdown_content.replace("\n- ", "\n\n- ")
    markdown_content = markdown_content.strip()
    
    # 保存为Markdown文件
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(markdown_content)
    
    print(f"Success! Markdown file generated: {output_path}")
    print(f"File size: {os.path.getsize(output_path)} bytes")
    
except Exception as e:
    print(f"Error during conversion: {str(e)}")
    import traceback
    traceback.print_exc()
    sys.exit(1)

print("Done!")
