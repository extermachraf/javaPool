# 📦 Java Module 04 - Packages and JAR Files

## 🔍 Overview

This module focuses on Java project organization, library creation, and utilizing external dependencies. Through a series of exercises, you'll learn how to structure Java code into packages, create JAR archives, and incorporate external libraries to enhance your application's functionality.

The project involves building an application that converts a BMP image into ASCII art, displaying it in the console with customizable characters. As you progress through the exercises, you'll improve the application's architecture and capabilities.

---

## 🚀 Exercise 00: Packages

### Concepts Learned
- **📁 Java Package Structure**: Organizing code logically into hierarchical packages
- **📂 File I/O**: Reading image files and processing binary data
- **💻 Console Output**: Formatting and displaying text in the terminal
- **🏗️ Code Organization**: Creating maintainable project structures
- **🖼️ BMP Image Processing**: Working with simple bitmap image format
- **⌨️ Parameter Handling**: Processing command-line arguments

> This exercise teaches the fundamentals of Java package organization while implementing a practical application that converts images to text characters. You'll learn how to structure your code properly to maintain clean separation of concerns between application logic and startup code.

---

## 📦 Exercise 01: First JAR

### Concepts Learned
- **📚 Java Archives (JAR)**: Creating executable Java archives
- **🗃️ Resource Management**: Including resources (images) within JAR files
- **📝 Manifest Files**: Configuring JAR entry points via manifest.txt
- **⚙️ Build Processes**: Setting up compilation and packaging workflows
- **🚚 Project Distribution**: Creating self-contained applications

> This exercise builds upon the previous one by teaching you how to package your application into a distributable JAR file. You'll learn how to include resources within your archive and configure the manifest to make your JAR executable.

---

## 🔌 Exercise 02: JCommander & JCDP

### Concepts Learned
- **📚 External Libraries**: Integrating third-party dependencies
- **🔍 Command-Line Parsing**: Using JCommander for robust command-line processing
- **🎨 Colored Console Output**: Enhancing the user interface with JCDP or JColor
- **🏗️ Advanced Project Organization**: Managing external dependencies
- **📥 Library Management**: Including and using external JAR files

> The final exercise introduces external library integration. You'll improve your application by using JCommander for better command-line argument parsing and add colored console output using either JCDP or JColor libraries. This teaches you how to leverage existing solutions to enhance your applications.

---

## 💡 Key Takeaways

Throughout this module, you'll gain practical experience with:

| Skill | Description |
|-------|-------------|
| 📁 Project Structure | Building well-structured Java applications using proper package organization |
| 📦 Distribution | Creating distributable Java applications via JAR archives |
| 🔌 Integration | Incorporating external libraries to extend application functionality |
| 🗃️ Resource Management | Managing project resources and dependencies |
| 🌟 Best Practices | Following industry standard practices for Java application development |

Each exercise progressively builds upon the knowledge gained in the previous one, culminating in a complete, well-structured Java application with external dependencies.

---

## 📝 Project Structure Overview

```
ImagesToChar/
├── 📁 src/
│   ├── 📁 java/
│   │   └── 📁 fr.42.printer/
│   │       ├── 📁 app/      # Application startup classes
│   │       └── 📁 logic/    # Image processing logic
│   └── 📁 resources/        # Project resources (images, etc.)
├── 📁 lib/                  # External libraries (Exercise 02)
├── 📁 target/               # Compiled classes and JAR files
└── 📄 README.txt            # Project documentation
```

> **Note**: The structure evolves across exercises as new concepts are introduced and implemented.