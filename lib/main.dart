import 'package:flutter/material.dart';
import 'package:flutura/themes/theme_provider.dart';
import 'package:provider/provider.dart';
import 'login.dart';
import 'sign_up.dart';
import 'otp_verification.dart';
import 'home_screen.dart';

void main() {
  runApp(ChangeNotifierProvider(
    create: (context) => ThemeProvider(),
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: const HomePage(),
      theme: Provider.of<ThemeProvider>(context).themeData,
      initialRoute: '/',
      routes: {
        '/': (context) => SignUpScreen(),
        '/login': (context) => LoginScreen(),
        '/otp': (context) => OTPVerificationScreen(),
      },
    );
  }
}
