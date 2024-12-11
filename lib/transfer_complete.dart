import 'package:flutter/material.dart';

class TransferCompleteScreen extends StatelessWidget {
  const TransferCompleteScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF4B39EF),
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            _buildTransferCompleteImage(),
            const SizedBox(height: 16),
            _buildTransferCompleteText(),
            const SizedBox(height: 12),
            _buildTransferCompleteDescription(),
            const SizedBox(height: 70),
            _buildOkayButton(context),
          ],
        ),
      ),
    );
  }

  Widget _buildTransferCompleteImage() {
    return Image.asset(
      'assets/images/transferComplete@2x.png',
      width: 250,
      height: 250,
      fit: BoxFit.fitHeight,
    );
  }

  Widget _buildTransferCompleteText() {
    return const Text(
      'Transfer Complete',
      style: TextStyle(
        fontFamily: 'Lexend',
        fontSize: 24,
        fontWeight: FontWeight.w600,
        color: Colors.white,
        letterSpacing: 0.0,
      ),
    );
  }

  Widget _buildTransferCompleteDescription() {
    return const Padding(
      padding: EdgeInsetsDirectional.fromSTEB(24, 0, 24, 0),
      child: Text(
        'Great work, you successfully transferred funds. It may take a few days for the funds to leave your account.',
        textAlign: TextAlign.center,
        style: TextStyle(
          fontFamily: 'Lexend',
          fontSize: 16,
          fontWeight: FontWeight.normal,
          color: Color(0xB3FFFFFF),
          letterSpacing: 0.0,
        ),
      ),
    );
  }

  Widget _buildOkayButton(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        // Navigate to the 'MY_Card' screen
        Navigator.pushNamed(context, 'MY_Card');
      },
      style: ElevatedButton.styleFrom(
        foregroundColor: Colors.white,
        backgroundColor: const Color(0xFF4B39EF),
        elevation: 2,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(40),
        ),
        minimumSize: const Size(130, 50),
      ),
      child: const Text(
        'Okay',
        style: TextStyle(
          fontFamily: 'Lexend',
          fontSize: 16,
          fontWeight: FontWeight.normal,
          letterSpacing: 0.0,
        ),
      ),
    );
  }
}
